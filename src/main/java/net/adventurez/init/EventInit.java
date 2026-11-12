package net.adventurez.init;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.adventurez.AdventureMain;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class EventInit {

    // Fabric's ServerPlayerEvents.AFTER_RESPAWN gives (oldPlayer, newPlayer, alive) at once. On NeoForge the old
    // player is only available in PlayerEvent.Clone, and the respawn is finished in PlayerRespawnEvent.
    private static final Map<UUID, Integer> PENDING_FAME = new HashMap<>();

    public static void init(IEventBus gameBus) {
        gameBus.addListener(PlayerEvent.Clone.class, event -> {
            if (!event.isWasDeath() && event.getOriginal() instanceof ServerPlayer oldPlayer && oldPlayer.hasEffect(EffectInit.FAME)) {
                PENDING_FAME.put(oldPlayer.getUUID(), oldPlayer.getEffect(EffectInit.FAME).getDuration());
            }
        });
        gameBus.addListener(PlayerEvent.PlayerRespawnEvent.class, event -> {
            Integer duration = PENDING_FAME.remove(event.getEntity().getUUID());
            if (duration != null && event.isEndConquered() && event.getEntity() instanceof ServerPlayer newPlayer) {
                newPlayer.addEffect(new MobEffectInstance(EffectInit.FAME, duration, 0, false, false, true));
            }
        });
    }

    public static void registerModEvents(IEventBus modBus) {
        modBus.addListener(AddPackFindersEvent.class, event -> {
            if (ModList.get().isLoaded("tooltiprareness")) {
                event.addPackFinders(AdventureMain.identifierOf("resourcepacks/tooltiprareness_adventurez"), PackType.SERVER_DATA, Component.literal("tooltiprareness_adventurez"),
                        PackSource.BUILT_IN, false, Pack.Position.TOP);
            }
        });
    }

}
