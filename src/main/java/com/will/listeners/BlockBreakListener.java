package com.will.listeners;

import com.will.Main;
import com.will.DropConfig;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {
    private final Main plugin;

    public BlockBreakListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Check if the block broken matches any of the configured drops
        DropConfig drop = plugin.getDropsConfiguration().getDropConfig(event.getBlock().getType(), event.getBlock().getBiome());

        if (drop != null) {
            if (drop.shouldDrop()) {
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