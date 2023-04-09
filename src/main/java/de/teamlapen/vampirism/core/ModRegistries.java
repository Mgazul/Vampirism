package de.teamlapen.vampirism.core;

import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.vampirism.api.entity.convertible.IConvertingHandler;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.task.Task;
import de.teamlapen.vampirism.api.items.oil.IOil;
import de.teamlapen.vampirism.world.gen.VampirismFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

import static de.teamlapen.vampirism.api.VampirismRegistries.*;

public class ModRegistries {

    static final DeferredRegister<ISkill<?>> DEFERRED_SKILLS = DeferredRegister.create(SKILLS_ID, SKILLS_ID.location().getNamespace());
    static final DeferredRegister<IAction<?>> DEFERRED_ACTIONS = DeferredRegister.create(ACTIONS_ID, ACTIONS_ID.location().getNamespace());
    static final DeferredRegister<IEntityAction> DEFERRED_ENTITY_ACTIONS = DeferredRegister.create(ENTITY_ACTIONS_ID, ENTITY_ACTIONS_ID.location().getNamespace());
    static final DeferredRegister<IMinionTask<?, ?>> DEFERRED_MINION_TASKS = DeferredRegister.create(MINION_TASKS_ID, MINION_TASKS_ID.location().getNamespace());
    static final DeferredRegister<Task> DEFERRED_TASKS = DeferredRegister.create(TASK_ID, TASK_ID.location().getNamespace());
    static final DeferredRegister<IRefinement> DEFERRED_REFINEMENTS = DeferredRegister.create(REFINEMENT_ID, REFINEMENT_ID.location().getNamespace());
    static final DeferredRegister<IRefinementSet> DEFERRED_REFINEMENT_SETS = DeferredRegister.create(REFINEMENT_SET_ID, REFINEMENT_SET_ID.location().getNamespace());
    static final DeferredRegister<IOil> DEFERRED_OILS = DeferredRegister.create(OILS_ID, OILS_ID.location().getNamespace());
    static final DeferredRegister<IConvertingHandler<?>> DEFERRED_CONVERTING_HANDLERS = DeferredRegister.create(CONVERTING_HANDLER_ID, CONVERTING_HANDLER_ID.location().getNamespace());

    public static final Supplier<IForgeRegistry<ISkill<?>>> SKILLS = DEFERRED_SKILLS.makeRegistry(RegistryBuilder::new);
    public static final Supplier<IForgeRegistry<IAction<?>>> ACTIONS = DEFERRED_ACTIONS.makeRegistry(RegistryBuilder::new);
    public static final Supplier<IForgeRegistry<IEntityAction>> ENTITY_ACTIONS = DEFERRED_ENTITY_ACTIONS.makeRegistry(RegistryBuilder::new);
    public static final Supplier<IForgeRegistry<IMinionTask<?, ?>>> MINION_TASKS = DEFERRED_MINION_TASKS.makeRegistry(RegistryBuilder::new);
    public static final Supplier<IForgeRegistry<Task>> TASKS = DEFERRED_TASKS.makeRegistry(RegistryBuilder::new);
    public static final Supplier<IForgeRegistry<IRefinement>> REFINEMENTS = DEFERRED_REFINEMENTS.makeRegistry(RegistryBuilder::new);
    public static final Supplier<IForgeRegistry<IRefinementSet>> REFINEMENT_SETS = DEFERRED_REFINEMENT_SETS.makeRegistry(RegistryBuilder::new);
    public static final Supplier<IForgeRegistry<IOil>> OILS = DEFERRED_OILS.makeRegistry(RegistryBuilder::new);
    public static final Supplier<IForgeRegistry<IConvertingHandler<?>>> CONVERTING_HANDLERS = DEFERRED_CONVERTING_HANDLERS.makeRegistry(RegistryBuilder::new);

    public static final RegistrySetBuilder DATA_BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, ModBiomes::createBiomes)
            .add(Registries.CONFIGURED_FEATURE, VampirismFeatures::createConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, VampirismFeatures::createPlacedFeatures)
            .add(Registries.STRUCTURE, ModFeatures::createStructures)
            .add(Registries.PROCESSOR_LIST, ModStructures::createStructureProcessorLists)
            .add(Registries.TEMPLATE_POOL, ModStructures::createStructurePoolTemplates)
            .add(Registries.STRUCTURE_SET, VampirismFeatures::createStructureSets)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, VampirismFeatures::createBiomeModifier)
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::createDamageTypes)
            ;

    static void init(IEventBus bus) {
        DEFERRED_SKILLS.register(bus);
        DEFERRED_ACTIONS.register(bus);
        DEFERRED_ENTITY_ACTIONS.register(bus);
        DEFERRED_MINION_TASKS.register(bus);
        DEFERRED_TASKS.register(bus);
        DEFERRED_REFINEMENTS.register(bus);
        DEFERRED_REFINEMENT_SETS.register(bus);
        DEFERRED_OILS.register(bus);
        DEFERRED_CONVERTING_HANDLERS.register(bus);
    }

}
