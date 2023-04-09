package com.will;

import com.moandjiezana.toml.Toml;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropsConfiguration {
    private final Main plugin;
    private File dropsFile;
    private Map<String, DropConfig> drops;

    public DropsConfiguration(Main plugin) {
        this.plugin = plugin;
        this.dropsFile = new File(plugin.getDataFolder(), "drops.toml");
        loadDrops();
    }

    public void loadDrops() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!dropsFile.exists()) {
            try {
                dropsFile.createNewFile();
                try (InputStream is = plugin.getResource("drops.toml");
                     OutputStream os = new FileOutputStream(dropsFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                } catch (IOException e) {
                    plugin.getLogger().warning("Failed to copy default drops.toml file.");
                    e.printStackTrace();
                }
            } catch (IOException e) {
                plugin.getLogger().warning("Failed to create drops.toml file.");
                e.printStackTrace();
            }
        }

        Toml toml = new Toml().read(dropsFile);
        Map<String, DropConfig> newDrops = new HashMap<>();
        List<HashMap<String, Object>> dropConfigs = toml.getList("drops");
        for (HashMap<String, Object> drop : dropConfigs) {
            String key = drop.get("block").toString() + ":" + drop.get("biome").toString();
            String item = drop.get("item").toString();
            double probability = (double) drop.get("probability");
            DropConfig config = new DropConfig(drop.get("block").toString(), drop.get("biome").toString(), item, probability);
            newDrops.put(key, config);
        }
        drops = newDrops;
    }

    public DropConfig getDropConfig(String key) {
        return drops.get(key);
    }
}

