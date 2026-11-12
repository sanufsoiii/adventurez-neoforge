package net.adventurez.network;

import net.adventurez.entity.DragonEntity;
import net.adventurez.network.packet.DragonFireBreathPacket;
import net.adventurez.network.packet.VelocityPacket;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class AdventureServerPacket {

    public static void registerModEvents(IEventBus modBus) {
        modBus.addListener(RegisterPayloadHandlersEvent.class, AdventureServerPacket::register);
    }

    private static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(DragonFireBreathPacket.PACKET_ID, DragonFireBreathPacket.PACKET_CODEC, (payload, context) -> {
            context.enqueueWork(() -> {
                if (context.player().getVehicle() instanceof DragonEntity) {
                    ((DragonEntity) context.player().getVehicle()).fireBreathActive = true;
                }
            });
        });
        // Client-bound handler lives in AdventureClientPacket; it only touches the client-side player and level.
        registrar.playToClient(VelocityPacket.PACKET_ID, VelocityPacket.PACKET_CODEC, AdventureClientPacket::handleVelocity);
    }

}
