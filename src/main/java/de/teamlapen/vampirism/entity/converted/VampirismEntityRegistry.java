package de.teamlapen.vampirism.entity.converted;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import de.teamlapen.vampirism.api.ThreadSafeAPI;
import de.teamlapen.vampirism.api.entity.BiteableEntry;
import de.teamlapen.vampirism.api.entity.IExtendedCreatureVampirism;
import de.teamlapen.vampirism.api.entity.IVampirismEntityRegistry;
import de.teamlapen.vampirism.api.entity.convertible.IConvertedCreature;
import de.teamlapen.vampirism.api.entity.convertible.IConvertingHandler;
import de.teamlapen.vampirism.util.RegUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class VampirismEntityRegistry implements IVampirismEntityRegistry {
    /**
     * Filled after ResourceManager reload (after 1 tick in game)
     * Stores biteable values
     */
    @NotNull
    public static final BiteableEntryManager biteableEntryManager = new BiteableEntryManager();
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Used to store convertible handlers after {@link FMLCommonSetupEvent}
     */
    @NotNull
    private final Map<EntityType<? extends PathfinderMob>, IConvertingHandler<?>> convertibles = new ConcurrentHashMap<>();
    @NotNull
    private final Map<EntityType<? extends PathfinderMob>, ResourceLocation> convertibleOverlay = new ConcurrentHashMap<>();
    /**
     * Stores custom extended creature constructors after {@link InterModEnqueueEvent}
     */
    private final Map<Class<? extends PathfinderMob>, Function<? extends PathfinderMob, IExtendedCreatureVampirism>> extendedCreatureConstructors = new ConcurrentHashMap<>();
    private Function<IConvertingHandler.IDefaultHelper, IConvertingHandler<?>> defaultConvertingHandlerCreator;

    /**
     * denies convertible addition after {@link InterModProcessEvent}
     */
    private boolean finished = false;

    @Override
    @ThreadSafeAPI
    public void addConvertible(EntityType<? extends PathfinderMob> type, ResourceLocation overlayLocation) {
        addConvertible(type, overlayLocation, new DefaultConvertingHandler<>(null));
    }

    @Override
    @ThreadSafeAPI
    public void addConvertible(EntityType<? extends PathfinderMob> type, ResourceLocation overlay_loc, IConvertingHandler.IDefaultHelper helper) {
        addConvertible(type, overlay_loc, defaultConvertingHandlerCreator.apply(helper));
    }

    @Override
    @ThreadSafeAPI
    public void addConvertible(EntityType<? extends PathfinderMob> type, @Nullable ResourceLocation overlay_loc, @NotNull IConvertingHandler<?> handler) {
        if (finished) throw new IllegalStateException("Register convertibles during InterModEnqueueEvent");
        convertibles.put(type, handler);

        if (FMLEnvironment.dist.isClient() && overlay_loc != null) {
            convertibleOverlay.put(type, overlay_loc);
        }
    }

    @Override
    @ThreadSafeAPI
    public <T extends PathfinderMob> void addCustomExtendedCreature(Class<? extends T> clazz, Function<T, IExtendedCreatureVampirism> constructor) {
        if (finished) throw new IllegalStateException("Register extended creatures during InterModEnqueueEvent");
        extendedCreatureConstructors.put(clazz, constructor);
    }

    public void applyNewResources(@NotNull Map<ResourceLocation, Float> valuesIn) {
        Map<ResourceLocation, Float> values = Maps.newHashMap(valuesIn);
        Map<ResourceLocation, BiteableEntry> biteables = Maps.newHashMap();
        Set<ResourceLocation> blacklist = Sets.newHashSet();
        final IConvertingHandler<?> defaultHandler = defaultConvertingHandlerCreator.apply(null);
        for (Map.Entry<EntityType<? extends PathfinderMob>, IConvertingHandler<?>> entry : convertibles.entrySet()) {
            ResourceLocation id = RegUtil.id(entry.getKey());
            if (id == null) {
                LOGGER.warn("Cannot register convertible {} since there is no EntityString for it", entry.getKey());
                continue;
            }
            Float bloodF = values.remove(id);
            if (bloodF == null) {
                LOGGER.warn("Missing blood value for convertible creature {} ({})", entry.getKey().getDescription(), id);
                continue;
            }
            int blood = Math.round(bloodF);
            LOGGER.debug("Registering convertible {} with blood {} and handler {}", entry.getKey().getDescription().getString(), blood, entry.getValue().getClass().getName());
            BiteableEntry biteEntry = new BiteableEntry(blood, (entry.getValue() == null ? defaultHandler : entry.getValue()));
            biteables.put(id, biteEntry);
        }
        LOGGER.info("Registered {} convertibles", biteables.size());
        for (Map.Entry<ResourceLocation, Float> entry : values.entrySet()) {
            if (!RegUtil.has(ForgeRegistries.ENTITY_TYPES, entry.getKey())) continue;
            int blood = Math.abs(Math.round(entry.getValue()));
            if (blood == 0) {
                blacklist.add(entry.getKey());
            } else {
                biteables.put(entry.getKey(), new BiteableEntry(blood));
            }
        }
        biteableEntryManager.setNewBiteables(biteables, blacklist);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public IConvertedCreature<?> convert(@NotNull PathfinderMob entity) {
        BiteableEntry b = biteableEntryManager.get(entity);
        if (b != null && b.convertingHandler != null) {
            return ((IConvertingHandler<PathfinderMob>) b.convertingHandler).createFrom(entity);
        }
        LOGGER.warn("Failed to find convertible entry for {}", entity);
        return null;
    }

    public void finishRegistration() {
        finished = true;
    }

    @NotNull
    @Override
    @OnlyIn(Dist.CLIENT)
    public Map<EntityType<? extends PathfinderMob>, ResourceLocation> getConvertibleOverlay() {
        return convertibleOverlay;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T extends PathfinderMob> Function<T, IExtendedCreatureVampirism> getCustomExtendedCreatureConstructor(@NotNull T entity) {
        return (Function<T, IExtendedCreatureVampirism>) extendedCreatureConstructors.get(entity.getClass());
    }

    @Nullable
    @Override
    public BiteableEntry getEntry(@NotNull PathfinderMob creature) {
        return biteableEntryManager.get(creature);
    }

    @Override
    public @Nullable BiteableEntry getOrCreateEntry(PathfinderMob creature) {
        return biteableEntryManager.getOrCalculate(creature);
    }

    /**
     * Set the creator for Vampirism's default converting handler
     * FOR INTERNAL USAGE ONLY
     */
    @ApiStatus.Internal
    public VampirismEntityRegistry setDefaultConvertingHandlerCreator(Function<IConvertingHandler.IDefaultHelper, IConvertingHandler<?>> creator) {
        this.defaultConvertingHandlerCreator = creator;
        return this;
    }
}