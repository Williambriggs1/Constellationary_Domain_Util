package com.will;

import com.will.listeners.BlockBreakListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private DropsConfiguration dropsConfiguration;

    @Override
    public void onEnable() {
        dropsConfiguration = new DropsConfiguration(this);

        PluginCommand command = getCommand("mypluginreload");
        if (command != null) {
            command.setExecutor(new DropsReloadCommand(this));
        } else {
            getLogger().warning("Failed to register command 'mypluginreload'");
        }

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

        getLogger().info("MyPlugin has been enabled!");
    }

    public DropsConfiguration getDropsConfiguration() {
        return dropsConfiguration;
    }
}
