package de.teamlapen.vampirism.data;

import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.core.*;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.*;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TagGenerator {

    public static void register(@NotNull GatherDataEvent event, @NotNull DataGenerator generator) {
        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new ModFluidTagsProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new ModPoiTypeProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new ModVillageProfessionProvider(generator, event.getExistingFileHelper()));
    }

    public static class ModBlockTagsProvider extends BlockTagsProvider {
        public ModBlockTagsProvider(@NotNull DataGenerator dataGenerator, ExistingFileHelper helper) {
            super(dataGenerator, REFERENCE.MODID, helper);
        }

        @NotNull
        @Override
        public String getName() {
            return REFERENCE.MODID + " " + super.getName();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void addTags() {
            tag(BlockTags.DIRT).add(ModBlocks.CURSED_EARTH.get(), ModBlocks.CURSED_GRASS.get());
            tag(ModTags.Blocks.CURSED_EARTH).add(ModBlocks.CURSED_EARTH.get(), ModBlocks.CURSED_GRASS.get());
            tag(ModTags.Blocks.CASTLE_BLOCK).add(ModBlocks.CASTLE_BLOCK_DARK_BRICK.get(), ModBlocks.CASTLE_BLOCK_DARK_BRICK_BLOODY.get(), ModBlocks.CASTLE_BLOCK_DARK_STONE.get(), ModBlocks.CASTLE_BLOCK_NORMAL_BRICK.get(), ModBlocks.CASTLE_BLOCK_PURPLE_BRICK.get());
            tag(ModTags.Blocks.CASTLE_SLAPS).add(ModBlocks.CASTLE_SLAB_DARK_BRICK.get(), ModBlocks.CASTLE_SLAB_DARK_STONE.get(), ModBlocks.CASTLE_SLAB_PURPLE_BRICK.get());
            tag(ModTags.Blocks.CASTLE_STAIRS).add(ModBlocks.CASTLE_STAIRS_DARK_STONE.get(), ModBlocks.CASTLE_STAIRS_DARK_BRICK.get(), ModBlocks.CASTLE_STAIRS_PURPLE_BRICK.get());
            tag(BlockTags.STAIRS).addTag(ModTags.Blocks.CASTLE_STAIRS);
            tag(BlockTags.SLABS).addTag(ModTags.Blocks.CASTLE_SLAPS);
            tag(BlockTags.FLOWER_POTS).add(ModBlocks.POTTED_VAMPIRE_ORCHID.get());

            // Tool Types
            tag(BlockTags.MINEABLE_WITH_SHOVEL)
                    .add(ModBlocks.CURSED_EARTH.get())
                    .add(ModBlocks.CURSED_GRASS.get())
            ;
            tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(ModBlocks.ALTAR_PILLAR.get())
                    .add(ModBlocks.ALTAR_TIP.get())
                    .add(ModBlocks.TOTEM_BASE.get())
                    .add(ModBlocks.BLOOD_PEDESTAL.get())
                    .add(ModBlocks.ALTAR_INFUSION.get())
                    .add(ModBlocks.WEAPON_TABLE.get())
                    .add(ModBlocks.GRAVE_CAGE.get())
                    .add(ModBlocks.TOMBSTONE1.get())
                    .add(ModBlocks.TOMBSTONE2.get())
                    .add(ModBlocks.TOMBSTONE3.get())
                    .add(ModBlocks.BLOOD_GRINDER.get())
                    .add(ModBlocks.FIRE_PLACE.get())
                    .add(ModBlocks.GRAVE_CAGE.get())
                    .add(ModBlocks.ALCHEMICAL_CAULDRON.get())
                    .add(ModBlocks.MED_CHAIR.get())
                    .add(ModBlocks.GARLIC_DIFFUSER_WEAK.get())
                    .add(ModBlocks.GARLIC_DIFFUSER_NORMAL.get())
                    .add(ModBlocks.GARLIC_DIFFUSER_IMPROVED.get())
                    .add(ModBlocks.CHANDELIER.get())
                    .add(ModBlocks.CANDELABRA.get())
                    .add(ModBlocks.CANDELABRA_WALL.get())
                    .add(ModBlocks.ALCHEMY_TABLE.get())
                    .addTag(ModTags.Blocks.CASTLE_BLOCK)
                    .addTag(ModTags.Blocks.CASTLE_SLAPS)
                    .addTag(ModTags.Blocks.CASTLE_STAIRS)
                    .addTag(ModTags.Blocks.TOTEM_TOP)
            ;
            tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(ModBlocks.ALTAR_INSPIRATION.get())
                    .add(ModBlocks.HUNTER_TABLE.get())
                    .add(ModBlocks.BLOOD_SIEVE.get())
                    .add(ModBlocks.ALTAR_CLEANSING.get())
                    .add(ModBlocks.DARK_SPRUCE_SIGN.get())
                    .add(ModBlocks.DARK_SPRUCE_WALL_SIGN.get())
                    .add(ModBlocks.CURSED_SPRUCE_SIGN.get())
                    .add(ModBlocks.CURSED_SPRUCE_WALL_SIGN.get())
                    .add(ModBlocks.BLOOD_CONTAINER.get())
                    .add(ModBlocks.POTION_TABLE.get())
                    .add(ModBlocks.CROSS.get())
                    .add(ModBlocks.DARK_SPRUCE_DOOR.get())
                    .add(ModBlocks.CURSED_SPRUCE_DOOR.get())
                    .add(ModBlocks.DARK_SPRUCE_TRAPDOOR.get())
                    .add(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get())
                    .add(ModBlocks.DARK_SPRUCE_FENCE_GATE.get())
                    .add(ModBlocks.CURSED_SPRUCE_FENCE_GATE.get())
                    .add(ModBlocks.VAMPIRE_RACK.get())
                    .add(ModBlocks.THRONE.get())
                    .addTag(ModTags.Blocks.COFFIN)
            ;

            // Tool Tiers
            tag(BlockTags.NEEDS_STONE_TOOL)
                    .add(ModBlocks.ALTAR_TIP.get())
                    .add(ModBlocks.GRAVE_CAGE.get())
                    .add(ModBlocks.MED_CHAIR.get())
                    .add(ModBlocks.MED_CHAIR.get())
                    .add(ModBlocks.CHANDELIER.get())
            ;
            tag(BlockTags.NEEDS_IRON_TOOL)
                    .add(ModBlocks.BLOOD_PEDESTAL.get())
                    .add(ModBlocks.BLOOD_GRINDER.get())
                    .add(ModBlocks.WEAPON_TABLE.get())
                    .add(ModBlocks.ALTAR_INFUSION.get())
                    .add(ModBlocks.ALCHEMICAL_CAULDRON.get())
                    .add(ModBlocks.CANDELABRA.get())
                    .add(ModBlocks.CANDELABRA_WALL.get())
                    .add(ModBlocks.ALCHEMY_TABLE.get())
            ;
            tag(BlockTags.NEEDS_DIAMOND_TOOL)
                    .add(ModBlocks.TOTEM_BASE.get())
                    .add(ModBlocks.ALTAR_INFUSION.get())
                    .add(ModBlocks.GARLIC_DIFFUSER_WEAK.get())
                    .add(ModBlocks.GARLIC_DIFFUSER_NORMAL.get())
                    .add(ModBlocks.GARLIC_DIFFUSER_IMPROVED.get())
                    .addTag(ModTags.Blocks.TOTEM_TOP_CRAFTED)
            ;

            tag(ModTags.Blocks.DARK_SPRUCE_LOG).add(ModBlocks.DARK_SPRUCE_LOG.get(), ModBlocks.STRIPPED_DARK_SPRUCE_LOG.get(), ModBlocks.DARK_SPRUCE_WOOD.get(), ModBlocks.STRIPPED_DARK_SPRUCE_WOOD.get());
            tag(ModTags.Blocks.CURSED_SPRUCE_LOG).add(ModBlocks.CURSED_SPRUCE_LOG.get(), ModBlocks.STRIPPED_CURSED_SPRUCE_LOG.get(), ModBlocks.CURSED_SPRUCE_WOOD.get(), ModBlocks.STRIPPED_CURSED_SPRUCE_WOOD.get());
            tag(BlockTags.LEAVES).add(ModBlocks.DARK_SPRUCE_LEAVES.get());
            tag(BlockTags.SAPLINGS).add(ModBlocks.DARK_SPRUCE_SAPLING.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.DARK_SPRUCE_TRAPDOOR.get(), ModBlocks.CURSED_SPRUCE_TRAPDOOR.get());
            tag(BlockTags.WOODEN_DOORS).add(ModBlocks.DARK_SPRUCE_DOOR.get(), ModBlocks.CURSED_SPRUCE_DOOR.get());
            tag(BlockTags.PLANKS).add(ModBlocks.DARK_SPRUCE_PLANKS.get(), ModBlocks.CURSED_SPRUCE_PLANKS.get());
            tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.DARK_SPRUCE_BUTTON.get(), ModBlocks.CURSED_SPRUCE_BUTTON.get());
            tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.DARK_SPRUCE_STAIRS.get(), ModBlocks.CURSED_SPRUCE_STAIRS.get());
            tag(BlockTags.WOODEN_SLABS).add(ModBlocks.DARK_SPRUCE_SLAB.get(), ModBlocks.CURSED_SPRUCE_SLAB.get());
            tag(BlockTags.WOODEN_FENCES).add(ModBlocks.DARK_SPRUCE_FENCE.get(), ModBlocks.CURSED_SPRUCE_FENCE.get());
            tag(BlockTags.LOGS_THAT_BURN).addTags(ModTags.Blocks.CURSED_SPRUCE_LOG, ModTags.Blocks.DARK_SPRUCE_LOG);
            tag(BlockTags.LOGS).addTags(ModTags.Blocks.CURSED_SPRUCE_LOG, ModTags.Blocks.DARK_SPRUCE_LOG);
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.DARK_SPRUCE_PRESSURE_PLACE.get(), ModBlocks.CURSED_SPRUCE_PRESSURE_PLACE.get());
            tag(BlockTags.WOODEN_DOORS).add(ModBlocks.DARK_SPRUCE_DOOR.get(), ModBlocks.CURSED_SPRUCE_DOOR.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.DARK_SPRUCE_TRAPDOOR.get(), ModBlocks.CURSED_SPRUCE_TRAPDOOR.get());
            tag(ModTags.Blocks.TOTEM_TOP_FRAGILE).add(ModBlocks.TOTEM_TOP.get(), ModBlocks.TOTEM_TOP_VAMPIRISM_HUNTER.get(), ModBlocks.TOTEM_TOP_VAMPIRISM_VAMPIRE.get());
            tag(ModTags.Blocks.TOTEM_TOP_CRAFTED).add(ModBlocks.TOTEM_TOP_CRAFTED.get(), ModBlocks.TOTEM_TOP_VAMPIRISM_HUNTER_CRAFTED.get(), ModBlocks.TOTEM_TOP_VAMPIRISM_VAMPIRE_CRAFTED.get());
            tag(ModTags.Blocks.TOTEM_TOP).addTag(ModTags.Blocks.TOTEM_TOP_FRAGILE).addTag(ModTags.Blocks.TOTEM_TOP_CRAFTED);
            tag(ModTags.Blocks.COFFIN).add(ModBlocks.COFFIN_RED.get()).add(ModBlocks.COFFIN_BLUE.get()).add(ModBlocks.COFFIN_GREEN.get()).add(ModBlocks.COFFIN_BROWN.get()).add(ModBlocks.COFFIN_BLACK.get()).add(ModBlocks.COFFIN_GRAY.get()).add(ModBlocks.COFFIN_LIGHT_BLUE.get()).add(ModBlocks.COFFIN_WHITE.get()).add(ModBlocks.COFFIN_LIGHT_GRAY.get()).add(ModBlocks.COFFIN_CYAN.get()).add(ModBlocks.COFFIN_PURPLE.get()).add(ModBlocks.COFFIN_PINK.get()).add(ModBlocks.COFFIN_LIME.get()).add(ModBlocks.COFFIN_YELLOW.get()).add(ModBlocks.COFFIN_ORANGE.get()).add(ModBlocks.COFFIN_MAGENTA.get());
        }
    }

    public static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(@NotNull DataGenerator dataGenerator, @NotNull BlockTagsProvider blockTagsProvider, ExistingFileHelper helper) {
            super(dataGenerator, blockTagsProvider, REFERENCE.MODID, helper);
        }

        @NotNull
        @Override
        public String getName() {
            return REFERENCE.MODID + " " + super.getName();
        }

        @Override
        protected void addTags() {
            copy(ModTags.Blocks.CASTLE_BLOCK, ModTags.Items.CASTLE_BLOCK);
            copy(ModTags.Blocks.CASTLE_STAIRS, ModTags.Items.CASTLE_STAIRS);
            copy(ModTags.Blocks.CASTLE_SLAPS, ModTags.Items.CASTLE_SLAPS);
            copy(ModTags.Blocks.CURSED_EARTH, ModTags.Items.CURSEDEARTH);
            copy(ModTags.Blocks.DARK_SPRUCE_LOG, ModTags.Items.DARK_SPRUCE_LOG);
            copy(ModTags.Blocks.CURSED_SPRUCE_LOG, ModTags.Items.CURSED_SPRUCE_LOG);
            copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
            copy(BlockTags.LOGS, ItemTags.LOGS);
            copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
            copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
            copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
            copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
            copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
            copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
            copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
            copy(BlockTags.PLANKS, ItemTags.PLANKS);
            copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
            copy(BlockTags.LEAVES, ItemTags.LEAVES);

            tag(ModTags.Items.CROSSBOW_ARROW).add(ModItems.CROSSBOW_ARROW_NORMAL.get(), ModItems.CROSSBOW_ARROW_SPITFIRE.get(), ModItems.CROSSBOW_ARROW_VAMPIRE_KILLER.get());
            tag(ModTags.Items.HUNTER_INTEL).add(ModItems.HUNTER_INTEL_0.get(), ModItems.HUNTER_INTEL_1.get(), ModItems.HUNTER_INTEL_2.get(), ModItems.HUNTER_INTEL_3.get(), ModItems.HUNTER_INTEL_4.get(), ModItems.HUNTER_INTEL_5.get(), ModItems.HUNTER_INTEL_6.get(), ModItems.HUNTER_INTEL_7.get(), ModItems.HUNTER_INTEL_8.get(), ModItems.HUNTER_INTEL_9.get());
            tag(ModTags.Items.PURE_BLOOD).add(ModItems.PURE_BLOOD_0.get(), ModItems.PURE_BLOOD_1.get(), ModItems.PURE_BLOOD_2.get(), ModItems.PURE_BLOOD_3.get(), ModItems.PURE_BLOOD_4.get());
            tag(ModTags.Items.VAMPIRE_CLOAK).add(ModItems.VAMPIRE_CLOAK_BLACK_BLUE.get(), ModItems.VAMPIRE_CLOAK_BLACK_RED.get(), ModItems.VAMPIRE_CLOAK_BLACK_WHITE.get(), ModItems.VAMPIRE_CLOAK_RED_BLACK.get(), ModItems.VAMPIRE_CLOAK_WHITE_BLACK.get());
            tag(ItemTags.SMALL_FLOWERS).add(ModBlocks.VAMPIRE_ORCHID.get().asItem());
            tag(ModTags.Items.GARLIC).add(ModItems.ITEM_GARLIC.get());
            tag(ModTags.Items.HOLY_WATER).add(ModItems.HOLY_WATER_BOTTLE_NORMAL.get(), ModItems.HOLY_WATER_BOTTLE_ENHANCED.get(), ModItems.HOLY_WATER_BOTTLE_ULTIMATE.get());
            tag(ModTags.Items.HOLY_WATER_SPLASH).add(ModItems.HOLY_WATER_SPLASH_BOTTLE_NORMAL.get(), ModItems.HOLY_WATER_SPLASH_BOTTLE_ENHANCED.get(), ModItems.HOLY_WATER_SPLASH_BOTTLE_ULTIMATE.get());
            tag(ItemTags.STAIRS).addTag(ModTags.Items.CASTLE_STAIRS);
            tag(ItemTags.SLABS).addTag(ModTags.Items.CASTLE_SLAPS);
            tag(ItemTags.PIGLIN_LOVED).add(ModItems.VAMPIRE_CLOTHING_CROWN.get());
            tag(ModTags.Items.HEART).add(ModItems.HUMAN_HEART.get(), ModItems.WEAK_HUMAN_HEART.get());
            tag(ItemTags.BOATS).add(ModItems.DARK_SPRUCE_BOAT.get(), ModItems.CURSED_SPRUCE_BOAT.get());
            tag(ModTags.Items.APPLICABLE_OIL_ARMOR).add(Items.LEATHER_BOOTS, Items.LEATHER_LEGGINGS, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, ModItems.VAMPIRE_CLOTHING_LEGS.get(), ModItems.VAMPIRE_CLOTHING_BOOTS.get(), ModItems.VAMPIRE_CLOTHING_CROWN.get(), ModItems.VAMPIRE_CLOTHING_HAT.get(), ModItems.VAMPIRE_CLOAK_RED_BLACK.get(), ModItems.VAMPIRE_CLOAK_BLACK_RED.get(), ModItems.VAMPIRE_CLOAK_BLACK_WHITE.get(), ModItems.VAMPIRE_CLOAK_WHITE_BLACK.get(), ModItems.VAMPIRE_CLOAK_BLACK_BLUE.get());
            tag(ModTags.Items.APPLICABLE_OIL_PICKAXE);
            tag(ModTags.Items.APPLICABLE_OIL_SWORD);
        }
    }

    public static class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
        public ModEntityTypeTagsProvider(@NotNull DataGenerator dataGenerator, ExistingFileHelper helper) {
            super(dataGenerator, REFERENCE.MODID, helper);
        }

        @Override
        protected void addTags() {
            tag(ModTags.Entities.HUNTER).add(ModEntities.HUNTER.get(), ModEntities.HUNTER_IMOB.get(), ModEntities.ADVANCED_HUNTER.get(), ModEntities.ADVANCED_HUNTER_IMOB.get(), ModEntities.HUNTER_TRAINER.get(), ModEntities.HUNTER_TRAINER.get(), ModEntities.HUNTER_TRAINER_DUMMY.get(), ModEntities.TASK_MASTER_HUNTER.get());
            tag(ModTags.Entities.VAMPIRE).add(ModEntities.VAMPIRE.get(), ModEntities.VAMPIRE_IMOB.get(), ModEntities.ADVANCED_VAMPIRE.get(), ModEntities.ADVANCED_VAMPIRE_IMOB.get(), ModEntities.VAMPIRE_BARON.get(), ModEntities.TASK_MASTER_VAMPIRE.get());
            tag(ModTags.Entities.ADVANCED_HUNTER).add(ModEntities.ADVANCED_HUNTER.get(), ModEntities.ADVANCED_HUNTER_IMOB.get());
            tag(ModTags.Entities.ADVANCED_VAMPIRE).add(ModEntities.ADVANCED_VAMPIRE.get(), ModEntities.ADVANCED_VAMPIRE_IMOB.get());
            tag(ModTags.Entities.ZOMBIES).add(EntityType.ZOMBIE, EntityType.HUSK, EntityType.DROWNED, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE_HORSE);
        }
    }

    public static class ModFluidTagsProvider extends FluidTagsProvider {
        public ModFluidTagsProvider(@NotNull DataGenerator generatorIn, ExistingFileHelper helper) {
            super(generatorIn, REFERENCE.MODID, helper);
        }

        @NotNull
        @Override
        public String getName() {
            return REFERENCE.MODID + " " + super.getName();
        }

        @Override
        protected void addTags() {
            tag(ModTags.Fluids.BLOOD).add(ModFluids.BLOOD.get());
            tag(ModTags.Fluids.IMPURE_BLOOD).add(ModFluids.IMPURE_BLOOD.get());
        }
    }

    public static class ModBiomeTagsProvider extends BiomeTagsProvider {

        public ModBiomeTagsProvider(@NotNull DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
            super(generator, REFERENCE.MODID, existingFileHelper);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void addTags() {
            tag(ModTags.Biomes.HasStructure.HUNTER_TENT).addTags(BiomeTags.IS_BADLANDS, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA).add(Biomes.PLAINS, Biomes.DESERT, Biomes.MEADOW, Biomes.SNOWY_PLAINS, Biomes.SPARSE_JUNGLE);
            tag(ModTags.Biomes.IS_FACTION_BIOME).addTags(ModTags.Biomes.IS_VAMPIRE_BIOME);
            tag(ModTags.Biomes.IS_VAMPIRE_BIOME).add(ModBiomes.VAMPIRE_FOREST.getKey());
            tag(ModTags.Biomes.IS_HUNTER_BIOME);
            tag(BiomeTags.IS_FOREST).add(ModBiomes.VAMPIRE_FOREST.getKey());
            tag(BiomeTags.IS_OVERWORLD).add(ModBiomes.VAMPIRE_FOREST.getKey());
            tag(Tags.Biomes.IS_DENSE_OVERWORLD).add(ModBiomes.VAMPIRE_FOREST.getKey());
            tag(Tags.Biomes.IS_MAGICAL).add(ModBiomes.VAMPIRE_FOREST.getKey());
            tag(Tags.Biomes.IS_SPOOKY).add(ModBiomes.VAMPIRE_FOREST.getKey());
            tag(ModTags.Biomes.HasStructure.VAMPIRE_DUNGEON).addTags(BiomeTags.IS_OVERWORLD);
            tag(ModTags.Biomes.HasSpawn.VAMPIRE).addTags(BiomeTags.IS_OVERWORLD); //TODO 1.19 determine spawn-able biomes
            tag(ModTags.Biomes.NoSpawn.VAMPIRE).addTags(ModTags.Biomes.IS_FACTION_BIOME);
            tag(ModTags.Biomes.HasSpawn.ADVANCED_VAMPIRE).addTags(BiomeTags.IS_OVERWORLD); //TODO 1.19 determine spawn-able biomes
            tag(ModTags.Biomes.NoSpawn.ADVANCED_VAMPIRE).addTags(ModTags.Biomes.IS_FACTION_BIOME);
            tag(ModTags.Biomes.HasSpawn.HUNTER).addTags(BiomeTags.IS_OVERWORLD); //TODO 1.19 determine spawn-able biomes
            tag(ModTags.Biomes.NoSpawn.HUNTER).addTags(ModTags.Biomes.IS_FACTION_BIOME);
            tag(ModTags.Biomes.HasSpawn.ADVANCED_HUNTER).addTags(BiomeTags.IS_OVERWORLD); //TODO 1.19 determine spawn-able biomes
            tag(ModTags.Biomes.NoSpawn.ADVANCED_HUNTER).addTags(ModTags.Biomes.IS_FACTION_BIOME);
        }
    }

    public static class ModPoiTypeProvider extends PoiTypeTagsProvider {

        public ModPoiTypeProvider(@NotNull DataGenerator p_236434_, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_236434_, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            tag(ModTags.PoiTypes.HAS_FACTION).add(ModVillage.NO_FACTION_TOTEM.get(), ModVillage.HUNTER_TOTEM.get(), ModVillage.VAMPIRE_TOTEM.get());
            tag(ModTags.PoiTypes.IS_HUNTER).add(ModVillage.HUNTER_TOTEM.get());
            tag(ModTags.PoiTypes.IS_VAMPIRE).add(ModVillage.VAMPIRE_TOTEM.get());
            tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(ModVillage.HUNTER_TOTEM.get(), ModVillage.VAMPIRE_TOTEM.get(), ModVillage.ALTAR_CLEANSING.get());
            tag(PoiTypeTags.VILLAGE).add(ModVillage.NO_FACTION_TOTEM.get(), ModVillage.HUNTER_TOTEM.get(), ModVillage.VAMPIRE_TOTEM.get(), ModVillage.ALTAR_CLEANSING.get());
        }
    }

    public static class ModVillageProfessionProvider extends TagsProvider<VillagerProfession> {

        public ModVillageProfessionProvider(@NotNull DataGenerator p_236434_, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_236434_, Registry.VILLAGER_PROFESSION, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            tag(ModTags.Professions.HAS_FACTION).add(ModVillage.HUNTER_EXPERT.get(), ModVillage.VAMPIRE_EXPERT.get());
            tag(ModTags.Professions.IS_VAMPIRE).add(ModVillage.VAMPIRE_EXPERT.get());
            tag(ModTags.Professions.IS_HUNTER).add(ModVillage.HUNTER_EXPERT.get());
        }
    }
}
