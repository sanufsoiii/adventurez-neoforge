package net.adventurez.init;

import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import net.adventurez.entity.client.DragonClientInput;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public class KeybindInit {

    public static KeyMapping dragonFlyDownKeyBind;
    public static KeyMapping dragonFireBreathBind;

    public static void init(IEventBus modBus, IEventBus gameBus) {
        // Keybinds
        dragonFlyDownKeyBind = new KeyMapping("key.adventurez.dragonflydown", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.adventurez.keybind");
        dragonFireBreathBind = new KeyMapping("key.adventurez.dragonfirebreath", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "category.adventurez.keybind");
        // Registering
        modBus.addListener(RegisterKeyMappingsEvent.class, event -> {
            event.register(dragonFlyDownKeyBind);
            event.register(dragonFireBreathBind);
        });
        // Callback (Fabric END_CLIENT_TICK)
        gameBus.addListener(ClientTickEvent.Post.class, event -> {
            Minecraft client = Minecraft.getInstance();
            if (dragonFlyDownKeyBind.isDown()) {
                DragonClientInput.flyDragonDown(client.player, dragonFlyDownKeyBind.saveString());
                return;
            } else if (dragonFireBreathBind.consumeClick()) {
                DragonClientInput.dragonFireBreath(client.player);
                return;
            }
        });
    }

}
