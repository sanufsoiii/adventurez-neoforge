package net.adventurez.mixin;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;

@Mixin(NaturalSpawner.class)
public class SpawnHelperMixin {

    private static final WeightedRandomList<MobSpawnSettings.SpawnerData> ADDED_SPAWNS;

    // PORT: NeoForge replaced the vanilla "return FORTRESS_ENEMIES" with a lookup of the fortress structure's
    // spawn overrides. The first instruction of that branch (registryAccess() ordinal 0) is the same point.
    @Inject(method = "mobsAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/StructureManager;registryAccess()Lnet/minecraft/core/RegistryAccess;", ordinal = 0), cancellable = true)
    private static void getSpawnEntriesMixin(ServerLevel world, StructureManager structureAccessor, ChunkGenerator chunkGenerator, MobCategory spawnGroup, BlockPos pos,
            @Nullable Holder<Biome> biomeEntry, CallbackInfoReturnable<WeightedRandomList<MobSpawnSettings.SpawnerData>> info) {
        List<MobSpawnSettings.SpawnerData> spawnersList = new ArrayList<>(NetherFortressStructure.FORTRESS_ENEMIES.unwrap());
        spawnersList.addAll(ADDED_SPAWNS.unwrap());
        info.setReturnValue(WeightedRandomList.create(spawnersList));
    }

    static {
        ADDED_SPAWNS = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityInit.NECROMANCER, ConfigInit.CONFIG.necromancer_spawn_weight, 1, 1),
                new MobSpawnSettings.SpawnerData(EntityInit.BLAZE_GUARDIAN, ConfigInit.CONFIG.blaze_guardian_spawn_weight, 1, 1));
    }
}
