package net.adventurez.entity.client;

import com.mojang.blaze3d.platform.InputConstants;

import net.adventurez.entity.DragonEntity;
import net.adventurez.network.packet.DragonFireBreathPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Client-only parts of DragonEntity (on Fabric they were inline behind isClientSide()). Kept in a separate class so
 * that DragonEntity does not reference client classes on a dedicated server.
 */
public class DragonClientInput {

    /** @return whether the rider holds the fly-down key */
    public static boolean handleRiderInput(DragonEntity dragon, LivingEntity livingEntity) {
        boolean shouldFlyDown = false;
        if (livingEntity instanceof LocalPlayer clientPlayerEntity) {
            shouldFlyDown = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), dragon.keyBind);
            float turningFloat = dragon.getTurningFloat();
            if (clientPlayerEntity.input.left) {
                turningFloat -= 0.05F;
            }
            if (!clientPlayerEntity.input.left && turningFloat < 0.0F) {
                turningFloat = 0.0F;
            }
            if (clientPlayerEntity.input.right) {
                turningFloat += 0.05F;
            }
            if (!clientPlayerEntity.input.right && turningFloat > 0.0F) {
                turningFloat = 0.0F;
            }
            dragon.setTurningFloat(turningFloat);
        }
        return shouldFlyDown;
    }

    public static void flyDragonDown(LocalPlayer player, String keyString) {
        if (player.getVehicle() != null && player.getVehicle() instanceof DragonEntity)
            ((DragonEntity) player.getVehicle()).setKeyBind(InputConstants.getKey(keyString).getValue());
    }

    public static void dragonFireBreath(LocalPlayer player) {
        if (player.getVehicle() != null && player.getVehicle() instanceof DragonEntity dragonEntity && player.getVehicle().isAlive()) {
            if (dragonEntity.getEntityData().get(DragonEntity.DRAGON_SIZE) >= 3) {
                PacketDistributor.sendToServer(new DragonFireBreathPacket());
                ((DragonEntity) player.getVehicle()).fireBreathActive = true;
            }
        }
    }
}
