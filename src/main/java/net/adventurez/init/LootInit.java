package net.adventurez.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import java.util.List;

public class LootInit {

    private static final List<ResourceKey<LootTable>> ADDED_LOOT_TABLE = List.of(BuiltInLootTables.PIGLIN_BARTERING, BuiltInLootTables.BASTION_BRIDGE, BuiltInLootTables.BASTION_HOGLIN_STABLE,
            BuiltInLootTables.BASTION_OTHER, BuiltInLootTables.BASTION_TREASURE);

    private static boolean addedLootTable(ResourceKey<LootTable> lootTable) {
        for (ResourceKey<LootTable> id : ADDED_LOOT_TABLE) {
            if (id.equals(lootTable)) {
                return true;
            }
        }
        return false;
    }

    public static void init(IEventBus gameBus) {
        gameBus.addListener(LootTableLoadEvent.class, event -> {
            ResourceKey<LootTable> key = event.getKey();
            LootTable table = event.getTable();
            if (addedLootTable(key)) {
                LootPool pool = LootPool.lootPool().add(LootItem.lootTableItem(ItemInit.GILDED_BLACKSTONE_SHARD)).setRolls(BinomialDistributionGenerator.binomial(1, 0.01F)).build();
                table.addPool(pool);
                if (BuiltInLootTables.BASTION_TREASURE.equals(key)) {
                    pool = LootPool.lootPool().add(LootItem.lootTableItem(ItemInit.GILDED_UPGRADE_SMITHING_TEMPLATE)).setRolls(BinomialDistributionGenerator.binomial(1, 0.5F)).build();
                    table.addPool(pool);
                }
            } else if ("minecraft:entities/piglin_brute".equals(key.location().toString())) {
                LootPool pool = LootPool.lootPool().add(LootItem.lootTableItem(ItemInit.GILDED_BLACKSTONE_SHARD)).setRolls(BinomialDistributionGenerator.binomial(1, 0.1F)).build();
                table.addPool(pool);
            }
        });

    }

}