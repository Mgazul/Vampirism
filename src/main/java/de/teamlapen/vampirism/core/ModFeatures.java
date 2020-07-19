package de.teamlapen.vampirism.core;


import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.util.REFERENCE;
import de.teamlapen.vampirism.world.gen.features.VampireDungeonFeature;
import de.teamlapen.vampirism.world.gen.structures.huntercamp.HunterCampPieces;
import de.teamlapen.vampirism.world.gen.structures.huntercamp.HunterCampStructure;
import de.teamlapen.vampirism.world.gen.util.BiomeTopBlockProcessor;
import de.teamlapen.vampirism.world.gen.util.RandomStructureProcessor;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ModFeatures {
    private static final Logger LOGGER = LogManager.getLogger();
    //features

    //structures
    public static final Structure<NoFeatureConfig> hunter_camp = new HunterCampStructure(NoFeatureConfig.field_236558_a_/*deserialize*/);
    //structure pieces
    public static final IStructurePieceType hunter_camp_fireplace = IStructurePieceType.register(HunterCampPieces.Fireplace::new, REFERENCE.MODID + ":hunter_camp_fireplace");
    public static final IStructurePieceType hunter_camp_tent = IStructurePieceType.register(HunterCampPieces.Tent::new, REFERENCE.MODID + ":hunter_camp_tent");
    public static final IStructurePieceType hunter_camp_special = IStructurePieceType.register(HunterCampPieces.SpecialBlock::new, REFERENCE.MODID + ":hunter_camp_craftingtable");

    public static final IStructureProcessorType<RandomStructureProcessor> random_selector = IStructureProcessorType.func_237139_a_/*register*/(REFERENCE.MODID+":random_selector", RandomStructureProcessor.CODEC);
    public static final IStructureProcessorType<BiomeTopBlockProcessor> biome_based = IStructureProcessorType.func_237139_a_/*register*/(REFERENCE.MODID+":biome_based", BiomeTopBlockProcessor.CODEC);

    public static final VampireDungeonFeature vampire_dungeon = new VampireDungeonFeature(NoFeatureConfig.field_236558_a_);

    static void registerFeatures(IForgeRegistry<Feature<?>> registry) {

        registry.register(vampire_dungeon.setRegistryName(REFERENCE.MODID, "vampire_dungeon"));
    }

    static void registerStructures(IForgeRegistry<Structure<?>> registry) {
        Structure.field_236385_u_.put(hunter_camp, GenerationStage.Decoration.SURFACE_STRUCTURES);
        Structure.field_236365_a_.put(REFERENCE.MODID + ":hunter_camp", hunter_camp);
        registry.register(hunter_camp.setRegistryName(REFERENCE.MODID, "hunter_camp"));
        Map<Structure<?>, StructureSeparationSettings> structureSettingsMapOverworld = DimensionSettings.Preset.field_236122_b_.func_236137_b_().func_236108_a_().func_236195_a_(); //TODO 1.16 Maybe check again when world gen/dimension stuff has stabilized in MC/Forge
        structureSettingsMapOverworld.put(hunter_camp, HunterCampStructure.getSettings());


        if (VampirismConfig.SERVER.villageModify.get()) {
            LOGGER.info("Replacing vanilla village structure separation settings for the overworld dimension preset");
            structureSettingsMapOverworld.put(Structure.field_236382_r_, new StructureSeparationSettings(VampirismConfig.SERVER.villageDistance.get(), VampirismConfig.SERVER.villageSeparation.get(), DimensionStructuresSettings.field_236191_b_.get(Structure.field_236382_r_).func_236673_c_()));
        } else {
            LOGGER.trace("Not modifying village");
        }

    }

    static void registerIgnoredBiomesForStructures() {
        VampirismAPI.worldGenRegistry().removeStructureFromBiomeCategories(hunter_camp.getRegistryName(), Lists.newArrayList(BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.END, BiomeDictionary.Type.NETHER, BiomeDictionary.Type.COLD, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.RIVER, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.JUNGLE));
    }
}
