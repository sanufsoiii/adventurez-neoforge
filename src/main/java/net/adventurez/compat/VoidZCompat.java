package net.adventurez.compat;

import java.lang.reflect.Field;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

/**
 * VoidZ (Globox_Z) exists only for Fabric. The original AdventureZ referenced its classes directly; this port
 * resolves the same ids by name so that nothing links against VoidZ. Ids checked against voidz-1.0.11+1.21.jar.
 * TODO-PORT: VoidZ compat untested (no NeoForge build of VoidZ exists).
 */
public class VoidZCompat {

    /** VoidZ DimensionInit.VOID_WORLD */
    public static final ResourceKey<Level> VOID_WORLD = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("voidz", "void"));

    /** VoidZ BlockInit.PORTAL */
    public static Block portal() {
        return block("void_portal");
    }

    /** VoidZ BlockInit.VOID_STONE */
    public static Block voidStone() {
        return block("void_stone");
    }

    /** VoidZ BlockInit.INFESTED_VOID */
    public static Block infestedVoidStone() {
        return block("infested_void_stone");
    }

    private static Block block(String path) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("voidz", path));
    }

    /** voidStone().defaultBlockState().setValue(VoidStoneBlock.ACTIVATED, activated) */
    public static BlockState activatedVoidStone(boolean activated) {
        BlockState state = voidStone().defaultBlockState();
        Property<?> property = state.getBlock().getStateDefinition().getProperty("activated_void");
        if (property instanceof BooleanProperty booleanProperty) {
            return state.setValue(booleanProperty, activated);
        }
        return state;
    }

    /** blockEntity instanceof PortalBlockEntity portal -> portal.bossTime = time */
    public static void setPortalBossTime(BlockEntity blockEntity, int time) {
        if (blockEntity == null || !ResourceLocation.fromNamespaceAndPath("voidz", "void_portal_entity").equals(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType()))) {
            return;
        }
        try {
            Field field = blockEntity.getClass().getField("bossTime");
            field.setInt(blockEntity, time);
        } catch (ReflectiveOperationException | RuntimeException ignored) {
        }
    }
}
