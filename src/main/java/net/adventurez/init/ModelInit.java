package net.adventurez.init;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ModelInit {

    public static void init(IEventBus modBus) {
        modBus.addListener(FMLClientSetupEvent.class, event -> event.enqueueWork(ModelInit::registerItemProperties));
    }

    private static void registerItemProperties() {
        ItemProperties.register(ItemInit.BLACKSTONE_GOLEM_ARM, ResourceLocation.parse("lavalight"), (stack, world, entity, seed) -> {
            if (stack.get(ItemInit.LAVA_LIGHT) != null && stack.get(ItemInit.LAVA_LIGHT)) {
                return 1F;
            }
            return 0F;
        });

    }

}
