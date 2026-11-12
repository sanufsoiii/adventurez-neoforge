package net.adventurez.init;

import com.mojang.serialization.MapCodec;
import net.adventurez.AdventureMain;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.adventurez.entity.*;

public class SpawnInit {

    public static void init() {
        Registry.register(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, AdventureMain.identifierOf("spawns"), SpawnBiomeModifier.CODEC);
    }

    public static void registerModEvents(IEventBus modBus) {
        modBus.addListener(RegisterSpawnPlacementsEvent.class, SpawnInit::setSpawnRestriction);
    }

    /**
     * Replaces Fabric's BiomeModifications.addSpawn: spawn weights come from the config at runtime, so a static
     * neoforge:add_spawns JSON cannot be used. data/adventurez/neoforge/biome_modifier/spawns.json uses this type.
     */
    public record SpawnBiomeModifier() implements BiomeModifier {
        public static final MapCodec<SpawnBiomeModifier> CODEC = MapCodec.unit(SpawnBiomeModifier::new);

        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD) {
                addSpawnEntries(biome, builder.getMobSpawnSettings());
            }
        }

        @Override
        public MapCodec<? extends BiomeModifier> codec() {
            return CODEC;
        }
    }

    // MONSTER tries to spawn often, CREATURE tries more rarely to spawn + in groups
    static void addSpawnEntries(Holder<Biome> biome, MobSpawnSettingsBuilder spawns) {
        addSpawn(spawns, biome.is(Biomes.BASALT_DELTAS), MobCategory.MONSTER, EntityInit.MINI_BLACKSTONE_GOLEM, ConfigInit.CONFIG.mini_blackstone_golem_spawn_weight,
                1, 1);
        addSpawn(spawns, biome.is(BiomeTags.IS_NETHER) && !biome.is(Biomes.BASALT_DELTAS), MobCategory.MONSTER, EntityInit.BLAZE_GUARDIAN,
                ConfigInit.CONFIG.blaze_guardian_spawn_weight, 1, 1);
        addSpawn(spawns, biome.is(BiomeTags.HAS_NETHER_FOSSIL), MobCategory.MONSTER, EntityInit.SOUL_REAPER, ConfigInit.CONFIG.nightmare_spawn_weight, 1, 1);

        addSpawn(spawns, biome.is(TagInit.IS_MUSHROOM), MobCategory.CREATURE, EntityInit.RED_FUNGUS, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3);
        addSpawn(spawns, biome.is(TagInit.IS_MUSHROOM), MobCategory.CREATURE, EntityInit.BROWN_FUNGUS, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3);
        addSpawn(spawns, biome.is(BiomeTags.HAS_VILLAGE_PLAINS), MobCategory.MONSTER, EntityInit.ORC, ConfigInit.CONFIG.orc_spawn_weight, 2, 4);
        addSpawn(spawns, biome.is(BiomeTags.HAS_IGLOO), MobCategory.CREATURE, EntityInit.MAMMOTH, ConfigInit.CONFIG.mammoth_spawn_weight, 2, 2);
        addSpawn(spawns, biome.is(BiomeTags.IS_END), MobCategory.CREATURE, EntityInit.ENDER_WHALE, ConfigInit.CONFIG.ender_whale_spawn_weight, 1, 1);
        addSpawn(spawns, biome.is(BiomeTags.IS_BADLANDS), MobCategory.CREATURE, EntityInit.IGUANA, ConfigInit.CONFIG.iguana_spawn_weight, 1, 2);
        addSpawn(spawns, biome.is(BiomeTags.HAS_DESERT_PYRAMID), MobCategory.MONSTER, EntityInit.DESERT_RHINO, ConfigInit.CONFIG.desert_rhino_spawn_weight, 1, 1);
        addSpawn(spawns, biome.is(BiomeTags.HAS_SWAMP_HUT), MobCategory.MONSTER, EntityInit.SHAMAN, ConfigInit.CONFIG.shaman_spawn_weight, 1, 1);
        addSpawn(spawns, biome.is(BiomeTags.IS_END) && !biome.is(Biomes.THE_END) && !biome.is(Biomes.END_BARRENS), MobCategory.MONSTER, EntityInit.ENDERWARTHOG,
                ConfigInit.CONFIG.enderwarthog_spawn_weight, 1, 2);
        addSpawn(spawns, biome.is(BiomeTags.IS_FOREST), MobCategory.CREATURE, EntityInit.DEER, ConfigInit.CONFIG.deer_spawn_weight, 2, 3);
        addSpawn(spawns, biome.is(BiomeTags.IS_FOREST), MobCategory.CREATURE, EntityInit.SKUNK, ConfigInit.CONFIG.skunk_spawn_weight, 1, 2);
        addSpawn(spawns, biome.is(BiomeTags.IS_END) && !biome.is(Biomes.THE_END) && !biome.is(Biomes.END_BARRENS), MobCategory.MONSTER, EntityInit.CRYSTAL_BEETLE,
                ConfigInit.CONFIG.crystal_beetle_spawn_weight, 2, 3);
    }

    private static void addSpawn(MobSpawnSettingsBuilder spawns, boolean selected, MobCategory category, EntityType<?> type, int weight, int minGroupSize,
            int maxGroupSize) {
        if (selected) {
            spawns.addSpawn(category, new MobSpawnSettings.SpawnerData(type, weight, minGroupSize, maxGroupSize));
        }
    }

    private static void setSpawnRestriction(RegisterSpawnPlacementsEvent event) {
        event.register(EntityInit.MINI_BLACKSTONE_GOLEM, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MiniBlackstoneGolemEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.NECROMANCER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NecromancerEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.SUMMONER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummonerEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.BLAZE_GUARDIAN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlazeGuardianEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.PIGLIN_BEAST, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.SOUL_REAPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SoulReaperEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.RED_FUNGUS, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RedFungusEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.BROWN_FUNGUS, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BrownFungusEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.ORC, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, OrcEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.MAMMOTH, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.ENDER_WHALE, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnderWhaleEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.IGUANA, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IguanaEntity::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.DESERT_RHINO, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DesertRhinoEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.SHAMAN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ShamanEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.DEER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.SKUNK, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.ENDERWARTHOG, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnderwarthogEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityInit.CRYSTAL_BEETLE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystalBeetleEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

}
