package de.teamlapen.vampirism.data;

import de.teamlapen.vampirism.blocks.*;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.util.REFERENCE;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;


public class BlockStateGenerator extends BlockStateProvider {


    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, REFERENCE.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //models
        models().getBuilder("coffin").texture("particle", mcLoc("block/spruce_planks"));
        models().withExistingParent("fire_side_alt0", modLoc("block/fire_side_alt")).texture("particle", mcLoc("block/fire_0")).texture("fire", mcLoc("block/fire_0"));
        models().withExistingParent("fire_side_alt1", modLoc("block/fire_side_alt")).texture("particle", mcLoc("block/fire_1")).texture("fire", mcLoc("block/fire_1"));
        models().withExistingParent("fire_side0", modLoc("block/fire_side")).texture("particle", mcLoc("block/fire_0")).texture("fire", mcLoc("block/fire_0"));
        models().withExistingParent("fire_side1", modLoc("block/fire_side")).texture("particle", mcLoc("block/fire_1")).texture("fire", mcLoc("block/fire_1"));
        models().withExistingParent("fire_floor0", modLoc("block/fire_floor")).texture("particle", mcLoc("block/fire_0")).texture("fire", mcLoc("block/fire_0"));
        models().withExistingParent("fire_floor1", modLoc("block/fire_floor")).texture("particle", mcLoc("block/fire_1")).texture("fire", mcLoc("block/fire_1"));
        models().withExistingParent("totem_top_vampirism_vampire", modLoc("block/totem_top"));
        models().withExistingParent("totem_top_vampirism_hunter", modLoc("block/totem_top"));

        //default blocks
        horizontalBlock(ModBlocks.hunter_table, models().getExistingFile(modLoc("block/hunter_table")));
        horizontalBlock(ModBlocks.garlic_beacon_normal, models().withExistingParent("garlic_beacon_normal", modLoc("block/garlic_beacon")));
        horizontalBlock(ModBlocks.garlic_beacon_weak, models().withExistingParent("garlic_beacon_weak", modLoc("block/garlic_beacon")));
        horizontalBlock(ModBlocks.garlic_beacon_improved, models().withExistingParent("garlic_beacon_improved", modLoc("block/garlic_beacon")));
        horizontalBlock(ModBlocks.church_altar, models().getExistingFile(modLoc("block/church_altar")));
        horizontalBlock(ModBlocks.blood_grinder, models().getExistingFile(modLoc("block/blood_grinder")));

        simpleBlock(ModBlocks.castle_block_dark_brick);
        simpleBlock(ModBlocks.castle_block_dark_brick_bloody);
        simpleBlock(ModBlocks.castle_block_dark_stone);
        simpleBlock(ModBlocks.castle_block_normal_brick);
        simpleBlock(ModBlocks.castle_block_purple_brick);
        simpleBlock(ModBlocks.cursed_earth);
        simpleBlock(ModBlocks.sunscreen_beacon, models().withExistingParent("vampirism:block/sunscreen_beacon", "minecraft:block/beacon").texture("beacon", "vampirism:block/cursed_earth"));
        simpleBlock(ModBlocks.coffin, models().getExistingFile(modLoc("block/coffin")));
        simpleBlock(ModBlocks.vampire_orchid, models().cross("vampire_orchid", modLoc("block/vampire_orchid")));
        simpleBlock(ModBlocks.totem_top, models().getExistingFile(modLoc("block/totem_top")));
        simpleBlock(ModBlocks.totem_top_vampirism_hunter, models().getExistingFile(modLoc("block/totem_top")));
        simpleBlock(ModBlocks.totem_top_vampirism_vampire, models().getExistingFile(modLoc("block/totem_top")));
        simpleBlock(ModBlocks.totem_base, models().getExistingFile(modLoc("block/totem_base")));
        simpleBlock(ModBlocks.altar_infusion, models().getExistingFile(modLoc("block/altar_infusion")));
        simpleBlock(ModBlocks.altar_inspiration, models().getExistingFile(modLoc("block/altar_inspiration/altar_inspiration")));
        simpleBlock(ModBlocks.altar_tip, models().getExistingFile(modLoc("block/altar_tip")));
        simpleBlock(ModBlocks.blood_container, models().getExistingFile(modLoc("block/blood_container/blood_container")));
        simpleBlock(ModBlocks.blood_pedestal, models().getExistingFile(modLoc("block/blood_pedestal")));
        simpleBlock(ModBlocks.blood_potion_table, models().getExistingFile(modLoc("block/blood_potion_table")));
        simpleBlock(ModBlocks.fire_place, models().getExistingFile(modLoc("block/fire_place")));
        simpleBlock(ModBlocks.potted_vampire_orchid, models().withExistingParent("vampirism:block/potted_vampire_orchid", "minecraft:block/flower_pot_cross").texture("plant", "vampirism:block/vampire_orchid"));

        stairsBlock(ModBlocks.castle_stairs_dark_stone, modLoc("block/castle_block_dark_stone"));
        stairsBlock(ModBlocks.castle_stairs_dark_brick, modLoc("block/castle_block_dark_brick"));
        stairsBlock(ModBlocks.castle_stairs_purple_brick, modLoc("block/castle_block_purple_brick"));

        slabBlock(ModBlocks.castle_slab_dark_brick, modLoc("block/castle_block_dark_brick"), modLoc("block/castle_block_dark_brick"));
        slabBlock(ModBlocks.castle_slab_dark_stone, modLoc("block/castle_block_dark_stone"), modLoc("block/castle_block_dark_stone"));
        slabBlock(ModBlocks.castle_slab_purple_brick, modLoc("block/castle_block_purple_brick"), modLoc("block/castle_block_purple_brick"));

        //variants

        getVariantBuilder(ModBlocks.garlic)
                .partialState().with(GarlicBlock.AGE, 0).modelForState().modelFile(models().getExistingFile(modLoc("block/garlic_stage_0"))).addModel()
                .partialState().with(GarlicBlock.AGE, 1).modelForState().modelFile(models().getExistingFile(modLoc("block/garlic_stage_0"))).addModel()
                .partialState().with(GarlicBlock.AGE, 2).modelForState().modelFile(models().getExistingFile(modLoc("block/garlic_stage_1"))).addModel()
                .partialState().with(GarlicBlock.AGE, 3).modelForState().modelFile(models().getExistingFile(modLoc("block/garlic_stage_1"))).addModel()
                .partialState().with(GarlicBlock.AGE, 4).modelForState().modelFile(models().getExistingFile(modLoc("block/garlic_stage_2"))).addModel()
                .partialState().with(GarlicBlock.AGE, 5).modelForState().modelFile(models().getExistingFile(modLoc("block/garlic_stage_2"))).addModel()
                .partialState().with(GarlicBlock.AGE, 6).modelForState().modelFile(models().getExistingFile(modLoc("block/garlic_stage_3"))).addModel()
                .partialState().with(GarlicBlock.AGE, 7).modelForState().modelFile(models().getExistingFile(modLoc("block/garlic_stage_3"))).addModel();

        getVariantBuilder(ModBlocks.altar_pillar)
                .partialState().with(AltarPillarBlock.TYPE_PROPERTY, AltarPillarBlock.EnumPillarType.NONE).modelForState().modelFile(models().getExistingFile(modLoc("block/altar_pillar"))).addModel()
                .partialState().with(AltarPillarBlock.TYPE_PROPERTY, AltarPillarBlock.EnumPillarType.BONE).modelForState().modelFile(models().withExistingParent("altar_pillar_filled_bone", modLoc("block/altar_pillar_filled")).texture("filler", mcLoc("block/bone_block_side"))).addModel()
                .partialState().with(AltarPillarBlock.TYPE_PROPERTY, AltarPillarBlock.EnumPillarType.GOLD).modelForState().modelFile(models().withExistingParent("altar_pillar_filled_gold", modLoc("block/altar_pillar_filled")).texture("filler", mcLoc("block/gold_block"))).addModel()
                .partialState().with(AltarPillarBlock.TYPE_PROPERTY, AltarPillarBlock.EnumPillarType.STONE).modelForState().modelFile(models().withExistingParent("altar_pillar_filled_stone_bricks", modLoc("block/altar_pillar_filled")).texture("filler", mcLoc("block/stone_bricks"))).addModel()
                .partialState().with(AltarPillarBlock.TYPE_PROPERTY, AltarPillarBlock.EnumPillarType.IRON).modelForState().modelFile(models().withExistingParent("altar_pillar_filled_iron", modLoc("block/altar_pillar_filled")).texture("filler", mcLoc("block/iron_block"))).addModel();

        ModelFile sieve = models().getExistingFile(modLoc("block/blood_sieve"));
        ModelFile activeSieve = models().getBuilder("active_blood_sieve").parent(sieve).texture("filter", modLoc("block/blood_sieve_filter_active"));
        getVariantBuilder(ModBlocks.blood_sieve)
                .partialState().with(SieveBlock.PROPERTY_ACTIVE, true).modelForState().modelFile(activeSieve).addModel()
                .partialState().with(SieveBlock.PROPERTY_ACTIVE, false).modelForState().modelFile(sieve).addModel();

        getVariantBuilder(ModBlocks.med_chair).forAllStates(blockState -> ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc(blockState.get(MedChairBlock.PART) == MedChairBlock.EnumPart.TOP ? "block/medchairhead" : "block/medchairbase"))).rotationY(((int) blockState.get(MedChairBlock.FACING).getHorizontalAngle() + 180) % 360).build());

        //multiparts

        getMultipartBuilder(ModBlocks.alchemical_fire)
                .part().modelFile(models().getExistingFile(modLoc("block/fire_floor0"))).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_floor1"))).addModel().end()
                .part().modelFile(models().getExistingFile(modLoc("block/fire_side0"))).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side1"))).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side_alt0"))).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side_alt1"))).addModel().end()
                .part().modelFile(models().getExistingFile(modLoc("block/fire_side0"))).rotationY(90).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side1"))).rotationY(90).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side_alt0"))).rotationY(90).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side_alt1"))).rotationY(90).addModel().end()
                .part().modelFile(models().getExistingFile(modLoc("block/fire_side0"))).rotationY(180).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side1"))).rotationY(180).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side_alt0"))).rotationY(180).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side_alt1"))).rotationY(180).addModel().end()
                .part().modelFile(models().getExistingFile(modLoc("block/fire_side0"))).rotationY(270).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side1"))).rotationY(270).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side_alt0"))).rotationY(270).nextModel().modelFile(models().getExistingFile(modLoc("block/fire_side_alt1"))).rotationY(270).addModel().end();

        ModelFile cauldronLiquid = models().getExistingFile(modLoc("block/alchemy_cauldron_liquid"));
        ModelFile cauldronLiquidBoiling = models().getBuilder("cauldron_boiling").parent(cauldronLiquid).texture("liquid", modLoc("block/blank_liquid_boiling"));
        getMultipartBuilder(ModBlocks.alchemical_cauldron)
                .part().modelFile(models().getExistingFile(modLoc("block/alchemy_cauldron"))).addModel().end()
                .part().modelFile(models().getExistingFile(modLoc("block/alchemy_cauldron_fire"))).addModel().condition(AlchemicalCauldronBlock.LIT, true).end()
                .part().modelFile(cauldronLiquid).addModel().condition(AlchemicalCauldronBlock.LIQUID, 1).end()
                .part().modelFile(cauldronLiquidBoiling).addModel().condition(AlchemicalCauldronBlock.LIQUID, 2).end();

        ModelFile tentModel = models().getExistingFile(modLoc("block/tent"));
        ModelFile tentBackLeft = models().getExistingFile(modLoc("block/tentback"));
        ModelFile tentBackRight = models().getExistingFile(modLoc("block/tentback_flipped"));

        ModelFile tentTR = models().getBuilder("tent_tr").parent(tentModel).texture("floor", modLoc("block/tent/floor_tr"));
        ModelFile tentTL = models().getBuilder("tent_tl").parent(tentModel).texture("floor", modLoc("block/tent/floor_tl"));
        ModelFile tentBL = models().getBuilder("tent_bl").parent(tentModel).texture("floor", modLoc("block/tent/floor_bl"));
        ModelFile tentBR = models().getBuilder("tent_br").parent(tentModel).texture("floor", modLoc("block/tent/floor_br"));
        Arrays.stream(new TentBlock[]{ModBlocks.tent, ModBlocks.tent_main}).forEach(t -> getMultipartBuilder(t)
                .part().modelFile(tentBR).rotationY(0).addModel().condition(TentBlock.FACING, Direction.NORTH).condition(TentBlock.POSITION, 0).end()
                .part().modelFile(tentBR).rotationY(90).addModel().condition(TentBlock.FACING, Direction.EAST).condition(TentBlock.POSITION, 0).end()
                .part().modelFile(tentBR).rotationY(180).addModel().condition(TentBlock.FACING, Direction.SOUTH).condition(TentBlock.POSITION, 0).end()
                .part().modelFile(tentBR).rotationY(270).addModel().condition(TentBlock.FACING, Direction.WEST).condition(TentBlock.POSITION, 0).end()
                .part().modelFile(tentBL).rotationY(0).addModel().condition(TentBlock.FACING, Direction.NORTH).condition(TentBlock.POSITION, 1).end()
                .part().modelFile(tentBL).rotationY(90).addModel().condition(TentBlock.FACING, Direction.EAST).condition(TentBlock.POSITION, 1).end()
                .part().modelFile(tentBL).rotationY(180).addModel().condition(TentBlock.FACING, Direction.SOUTH).condition(TentBlock.POSITION, 1).end()
                .part().modelFile(tentBL).rotationY(270).addModel().condition(TentBlock.FACING, Direction.WEST).condition(TentBlock.POSITION, 1).end()
                .part().modelFile(tentTL).rotationY(0).addModel().condition(TentBlock.FACING, Direction.NORTH).condition(TentBlock.POSITION, 2).end()
                .part().modelFile(tentTL).rotationY(90).addModel().condition(TentBlock.FACING, Direction.EAST).condition(TentBlock.POSITION, 2).end()
                .part().modelFile(tentTL).rotationY(180).addModel().condition(TentBlock.FACING, Direction.SOUTH).condition(TentBlock.POSITION, 2).end()
                .part().modelFile(tentTL).rotationY(270).addModel().condition(TentBlock.FACING, Direction.WEST).condition(TentBlock.POSITION, 2).end()
                .part().modelFile(tentTR).rotationY(0).addModel().condition(TentBlock.FACING, Direction.NORTH).condition(TentBlock.POSITION, 3).end()
                .part().modelFile(tentTR).rotationY(90).addModel().condition(TentBlock.FACING, Direction.EAST).condition(TentBlock.POSITION, 3).end()
                .part().modelFile(tentTR).rotationY(180).addModel().condition(TentBlock.FACING, Direction.SOUTH).condition(TentBlock.POSITION, 3).end()
                .part().modelFile(tentTR).rotationY(270).addModel().condition(TentBlock.FACING, Direction.WEST).condition(TentBlock.POSITION, 3).end()
                .part().modelFile(tentBackLeft).rotationY(180).addModel().condition(TentBlock.FACING, Direction.NORTH).condition(TentBlock.POSITION, 2).end()
                .part().modelFile(tentBackLeft).rotationY(270).addModel().condition(TentBlock.FACING, Direction.EAST).condition(TentBlock.POSITION, 2).end()
                .part().modelFile(tentBackLeft).rotationY(0).addModel().condition(TentBlock.FACING, Direction.SOUTH).condition(TentBlock.POSITION, 2).end()
                .part().modelFile(tentBackLeft).rotationY(90).addModel().condition(TentBlock.FACING, Direction.WEST).condition(TentBlock.POSITION, 2).end()
                .part().modelFile(tentBackRight).rotationY(0).addModel().condition(TentBlock.FACING, Direction.NORTH).condition(TentBlock.POSITION, 3).end()
                .part().modelFile(tentBackRight).rotationY(90).addModel().condition(TentBlock.FACING, Direction.EAST).condition(TentBlock.POSITION, 3).end()
                .part().modelFile(tentBackRight).rotationY(180).addModel().condition(TentBlock.FACING, Direction.SOUTH).condition(TentBlock.POSITION, 3).end()
                .part().modelFile(tentBackRight).rotationY(270).addModel().condition(TentBlock.FACING, Direction.WEST).condition(TentBlock.POSITION, 3).end());

        ModelFile weaponTable = models().getExistingFile(modLoc("block/weapon_table/weapon_table"));
        ModelFile weaponTableL1 = models().getExistingFile(modLoc("block/weapon_table/weapon_table_lava1"));
        ModelFile weaponTableL2 = models().getExistingFile(modLoc("block/weapon_table/weapon_table_lava2"));
        ModelFile weaponTableL3 = models().getExistingFile(modLoc("block/weapon_table/weapon_table_lava3"));
        ModelFile weaponTableL4 = models().getExistingFile(modLoc("block/weapon_table/weapon_table_lava4"));
        ModelFile weaponTableL5 = models().getExistingFile(modLoc("block/weapon_table/weapon_table_lava5"));

        getMultipartBuilder(ModBlocks.weapon_table)
                .part().modelFile(weaponTable).rotationY(0).addModel().condition(WeaponTableBlock.FACING, Direction.NORTH).end()
                .part().modelFile(weaponTable).rotationY(90).addModel().condition(WeaponTableBlock.FACING, Direction.EAST).end()
                .part().modelFile(weaponTable).rotationY(180).addModel().condition(WeaponTableBlock.FACING, Direction.SOUTH).end()
                .part().modelFile(weaponTable).rotationY(270).addModel().condition(WeaponTableBlock.FACING, Direction.WEST).end()
                .part().modelFile(weaponTableL1).rotationY(0).addModel().condition(WeaponTableBlock.FACING, Direction.NORTH).condition(WeaponTableBlock.LAVA, 1).end()
                .part().modelFile(weaponTableL1).rotationY(90).addModel().condition(WeaponTableBlock.FACING, Direction.EAST).condition(WeaponTableBlock.LAVA, 1).end()
                .part().modelFile(weaponTableL1).rotationY(180).addModel().condition(WeaponTableBlock.FACING, Direction.SOUTH).condition(WeaponTableBlock.LAVA, 1).end()
                .part().modelFile(weaponTableL1).rotationY(270).addModel().condition(WeaponTableBlock.FACING, Direction.WEST).condition(WeaponTableBlock.LAVA, 1).end()
                .part().modelFile(weaponTableL2).rotationY(0).addModel().condition(WeaponTableBlock.FACING, Direction.NORTH).condition(WeaponTableBlock.LAVA, 2).end()
                .part().modelFile(weaponTableL2).rotationY(90).addModel().condition(WeaponTableBlock.FACING, Direction.EAST).condition(WeaponTableBlock.LAVA, 2).end()
                .part().modelFile(weaponTableL2).rotationY(180).addModel().condition(WeaponTableBlock.FACING, Direction.SOUTH).condition(WeaponTableBlock.LAVA, 2).end()
                .part().modelFile(weaponTableL2).rotationY(270).addModel().condition(WeaponTableBlock.FACING, Direction.WEST).condition(WeaponTableBlock.LAVA, 2).end()
                .part().modelFile(weaponTableL3).rotationY(0).addModel().condition(WeaponTableBlock.FACING, Direction.NORTH).condition(WeaponTableBlock.LAVA, 3).end()
                .part().modelFile(weaponTableL3).rotationY(90).addModel().condition(WeaponTableBlock.FACING, Direction.EAST).condition(WeaponTableBlock.LAVA, 3).end()
                .part().modelFile(weaponTableL3).rotationY(180).addModel().condition(WeaponTableBlock.FACING, Direction.SOUTH).condition(WeaponTableBlock.LAVA, 3).end()
                .part().modelFile(weaponTableL3).rotationY(270).addModel().condition(WeaponTableBlock.FACING, Direction.WEST).condition(WeaponTableBlock.LAVA, 3).end()
                .part().modelFile(weaponTableL4).rotationY(0).addModel().condition(WeaponTableBlock.FACING, Direction.NORTH).condition(WeaponTableBlock.LAVA, 4).end()
                .part().modelFile(weaponTableL4).rotationY(90).addModel().condition(WeaponTableBlock.FACING, Direction.EAST).condition(WeaponTableBlock.LAVA, 4).end()
                .part().modelFile(weaponTableL4).rotationY(180).addModel().condition(WeaponTableBlock.FACING, Direction.SOUTH).condition(WeaponTableBlock.LAVA, 4).end()
                .part().modelFile(weaponTableL4).rotationY(270).addModel().condition(WeaponTableBlock.FACING, Direction.WEST).condition(WeaponTableBlock.LAVA, 4).end()
                .part().modelFile(weaponTableL5).rotationY(0).addModel().condition(WeaponTableBlock.FACING, Direction.NORTH).condition(WeaponTableBlock.LAVA, 5).end()
                .part().modelFile(weaponTableL5).rotationY(90).addModel().condition(WeaponTableBlock.FACING, Direction.EAST).condition(WeaponTableBlock.LAVA, 5).end()
                .part().modelFile(weaponTableL5).rotationY(180).addModel().condition(WeaponTableBlock.FACING, Direction.SOUTH).condition(WeaponTableBlock.LAVA, 5).end()
                .part().modelFile(weaponTableL5).rotationY(270).addModel().condition(WeaponTableBlock.FACING, Direction.WEST).condition(WeaponTableBlock.LAVA, 5).end();

    }
}
