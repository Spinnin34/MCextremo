package p.mcextremo.commads;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigReloadManager {
    private final JavaPlugin plugin;

    public ConfigReloadManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        loadConfig("vidas.yml");
        loadConfig("chat.yml");
        loadConfig("config.yml");
        // Ajusta los nombres de archivo según lo que realmente uses en tu plugin
    }

    private void loadConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.getLogger().warning("El archivo de configuración " + fileName + " no existe.");
            return;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        try {
            config.load(configFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            plugin.getLogger().warning("Error al cargar el archivo de configuración " + fileName + ": " + e.getMessage());
        }

        plugin.saveConfig();
    }
}
