package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.DragonEntity;
import net.adventurez.init.ConfigInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isPassenger() && player.getVehicle() instanceof DragonEntity dragonEntity && player.canUsePortal(true) && ConfigInit.CONFIG.allow_ender_dragon_nether_portal) {
            player.stopRiding();
            dragonEntity.portalProcess.setAsInsidePortalThisTick(true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;

    }

}