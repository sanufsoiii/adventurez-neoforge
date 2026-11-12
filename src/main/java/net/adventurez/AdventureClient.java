package net.adventurez;

import me.shedaniel.autoconfig.AutoConfig;
import net.adventurez.config.AdventureConfig;
import net.adventurez.init.KeybindInit;
import net.adventurez.init.ModelInit;
import net.adventurez.init.RenderInit;
import net.adventurez.port.assets.AssetNotice;
import net.adventurez.port.assets.OriginalAssetsPackFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(value = AdventureMain.MOD_ID, dist = Dist.CLIENT)
public class AdventureClient {

    private static final Logger LOGGER = LoggerFactory.getLogger("adventurez");
    private static boolean titleReached = false;

    public AdventureClient(IEventBus modBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) -> AutoConfig.getConfigScreen(AdventureConfig.class, parent).get());

        KeybindInit.init(modBus, NeoForge.EVENT_BUS);
        ModelInit.init(modBus);
        RenderInit.init(modBus);

        // Original ARR assets from the player's own jar in resourcepacks/ (never bundled, never downloaded).
        modBus.addListener(AddPackFindersEvent.class, OriginalAssetsPackFinder::onAddPackFinders);

        // Port self-check: log when the title screen is reached, optionally exit (runClientSmoke).
        NeoForge.EVENT_BUS.addListener(ScreenEvent.Opening.class, AdventureClient::onScreenOpening);
        NeoForge.EVENT_BUS.addListener(ScreenEvent.Opening.class, AssetNotice::onScreenOpening);
    }

    private static void onScreenOpening(ScreenEvent.Opening event) {
        if (titleReached || !(event.getNewScreen() instanceof TitleScreen)) {
            return;
        }
        titleReached = true;
        LOGGER.info("[adventurez-port] TITLE_SCREEN_REACHED");
        if (Boolean.getBoolean("adventurez.port.exitOnTitle")) {
            Thread exitThread = new Thread(() -> {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException ignored) {
                }
                Minecraft.getInstance().execute(() -> Minecraft.getInstance().stop());
            }, "adventurez-port-smoke-exit");
            exitThread.setDaemon(true);
            exitThread.start();
        }
    }

}
