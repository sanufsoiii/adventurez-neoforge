package net.adventurez;

import net.adventurez.init.*;
import net.adventurez.network.AdventureServerPacket;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

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

        // Method references only: calling a static method of a *Init class here would class-load it (and run its
        // Registry.register static initializers) outside RegisterEvent.
        modBus.addListener(EntityAttributeCreationEvent.class, EntityInit::registerAttributes);
        modBus.addListener(RegisterSpawnPlacementsEvent.class, SpawnInit::setSpawnRestriction);
        AdventureServerPacket.registerModEvents(modBus);
        EventInit.registerModEvents(modBus);

        LootInit.init(NeoForge.EVENT_BUS);
        NeoForge.EVENT_BUS.addListener(RegisterBrewingRecipesEvent.class, ItemInit::registerBrewingRecipes);
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
