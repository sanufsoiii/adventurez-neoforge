package net.adventurez;

import net.adventurez.init.*;
import net.adventurez.network.AdventureServerPacket;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(AdventureMain.MOD_ID)
public class AdventureMain {

    public static final String MOD_ID = "adventurez";

    private static boolean registered = false;

    public AdventureMain(IEventBus modBus) {
        // Config first: registration and spawn code reads ConfigInit.CONFIG.
        ConfigInit.init();

        // Vanilla registries are unfrozen for the whole RegisterEvent phase. The *Init classes register
        // their content from static fields (Fabric style), so they are class-loaded inside that phase.
        modBus.addListener(RegisterEvent.class, AdventureMain::onRegister);

        EntityInit.registerModEvents(modBus);
        SpawnInit.registerModEvents(modBus);
        AdventureServerPacket.registerModEvents(modBus);
        EventInit.registerModEvents(modBus);

        LootInit.init(NeoForge.EVENT_BUS);
        ItemInit.registerGameEvents(NeoForge.EVENT_BUS);
        EventInit.init(NeoForge.EVENT_BUS);
    }

    private static void onRegister(RegisterEvent event) {
        if (registered) {
            return;
        }
        registered = true;
        // Same order as the original onInitialize()
        BlockInit.init();
        EffectInit.init();
        EntityInit.init();
        ItemInit.init();
        ParticleInit.init();
        SoundInit.init();
        SpawnInit.init();
        TagInit.init();
    }

    public static ResourceLocation identifierOf(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}

// You are LOVED!!!
// Jesus loves you unconditional!
