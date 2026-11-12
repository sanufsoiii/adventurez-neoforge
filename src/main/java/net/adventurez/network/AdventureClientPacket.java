package net.adventurez.network;

import net.adventurez.network.packet.VelocityPacket;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class AdventureClientPacket {

    public static void handleVelocity(VelocityPacket payload, IPayloadContext context) {
        int entityId = payload.entityId();
        float velocity = payload.velocity();
        context.enqueueWork(() -> {
            Entity entity = context.player().level().getEntity(entityId);
            entity.push(0.0D, velocity, 0.0D);
        });
    }

}
