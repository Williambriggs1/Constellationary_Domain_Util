package com.will;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DropsReloadCommand implements CommandExecutor {
    private final Main plugin;

    public DropsReloadCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mypluginreload")) {
            plugin.getDropsConfiguration().loadDrops();
            sender.sendMessage("Drops configuration reloaded.");
            return true;
        }
        return false;
    }
}
