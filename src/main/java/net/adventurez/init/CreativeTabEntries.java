package net.adventurez.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

/**
 * Replaces Fabric's ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> entries.accept(item)):
 * items are collected in registration order and emitted by the tab's displayItems generator.
 */
public class CreativeTabEntries {

    private static final List<Item> ENTRIES = new ArrayList<>();

    public static void add(ResourceKey<CreativeModeTab> tab, Item item) {
        if (!tab.equals(ItemInit.ADVENTUREZ_ITEM_GROUP)) {
            throw new IllegalArgumentException("Unknown creative tab " + tab.location());
        }
        ENTRIES.add(item);
    }

    public static List<Item> entries() {
        return Collections.unmodifiableList(ENTRIES);
    }
}
