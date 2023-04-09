package com.will.listeners;

import com.will.Main;
import com.will.DropConfig;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BlockBreakListener implements Listener {
    private final Main plugin;
    private final Random random;

    public BlockBreakListener(Main plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Check if the block broken matches any of the configured drops
        String key = event.getBlock().getType() + ":" + event.getBlock().getBiome();
        DropConfig drop = plugin.getDropsConfiguration().getDropConfig(key);

        if (drop != null) {
            double probability = drop.probability();

            if (random.nextDouble() < probability) {
                // Optimize by fetching MMOItem only when required
                MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get("INGREDIENTS"), drop.item());
                if (mmoItem == null) {
                    plugin.getLogger().warning("Failed to find MMOItem: " + drop.item());
                    return;
                }
                ItemStack itemStack = mmoItem.newBuilder().build();
                if (itemStack != null) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), itemStack);
                } else {
                    plugin.getLogger().warning("Failed to create ItemStack for MMOItem: " + drop.item());
                }
            }
        }
    }
}