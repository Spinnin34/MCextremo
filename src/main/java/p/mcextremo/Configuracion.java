package p.mcextremo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Configuracion {
    private static FileConfiguration config;

    public static String prefijo;
    public static String mensajeActivado;
    public static String mensajeDesactivado;
    public static String developer;
    public static String titulo;
    public static String vidas;
    public static String vidasRestantes;
    public static String ban;

    public static void cargarYConfigurar(JavaPlugin plugin) {
        // Obtener o crear la configuración
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            // Configuración predeterminada
            config.addDefault("prefijo", "§x§F§B§0§0§0§0§lM§x§D§C§0§0§0§0§lC §x§B§C§0§0§0§0§lE§x§9§D§0§0§0§0§lX§x§7§E§0§0§0§0§lT§x§5§E§0§0§0§0§lR§x§3§F§0§0§0§0§lE§x§1§F§0§0§0§0§lM§x§0§0§0§0§0§0§lO");
            config.addDefault("mensaje_activado", "§x§A§2§F§B§7§E¡ᴇʟ ᴘᴠᴘ ᴇsᴛᴀ ᴀʜᴏʀᴀ ᴀᴄᴛɪᴠᴏ!");
            config.addDefault("mensaje_desactivado", "§x§F§B§5§D§5§D¡ᴇʟ ᴘᴠᴘ ᴇsᴛᴀ ᴀʜᴏʀᴀ ᴅᴇsᴀᴄᴛɪᴠᴀᴅᴏ!");
            config.addDefault("developer", "§7By the Developer ©Spinnin34");
            config.addDefault("titulo", "§x§F§B§0§0§0§0§lM§x§D§C§0§0§0§0§lC §x§B§C§0§0§0§0§lE§x§9§D§0§0§0§0§lX§x§7§E§0§0§0§0§lT§x§5§E§0§0§0§0§lR§x§3§F§0§0§0§0§lE§x§1§F§0§0§0§0§lM§x§0§0§0§0§0§0§lO");
            config.addDefault("vidas", "§fᴛɪᴇɴᴇ ᴠɪᴅᴀꜱ ʀᴇꜱᴛᴀɴᴛᴇꜱ:§x§F§B§0§0§7§1");
            config.addDefault("vidas_restantes", "§x§F§B§4§7§4§7ʜᴀꜱ ᴘᴇʀᴅɪᴅᴏ ᴜɴᴀ ᴠɪᴅᴀ.");
            config.addDefault("ban", "§x§F§B§0§0§7§1ʜᴀꜱ ᴘᴇʀᴅɪᴅᴏ ᴛᴏᴅᴀs ᴛᴜꜱ ᴠɪᴅᴀꜱ. ʜᴀꜱ ꜱɪᴅᴏ ʙᴀɴᴇᴀᴅᴏ.");
            // ... (resto de la configuración predeterminada)


            // Guardar configuración
            try {
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // Cargar configuración en variables
        prefijo = ChatColor.translateAlternateColorCodes('&', config.getString("prefijo"));
        mensajeActivado = ChatColor.translateAlternateColorCodes('&', config.getString("mensaje_activado"));
        mensajeDesactivado = ChatColor.translateAlternateColorCodes('&', config.getString("mensaje_desactivado"));
        developer = ChatColor.translateAlternateColorCodes('&', config.getString("developer"));
        titulo = ChatColor.translateAlternateColorCodes('&', config.getString("titulo"));
        vidas = ChatColor.translateAlternateColorCodes('&', config.getString("vidas"));
        vidasRestantes = ChatColor.translateAlternateColorCodes('&', config.getString("vidas_restantes"));
        ban = ChatColor.translateAlternateColorCodes('&', config.getString("ban"));
    }

    public static String getPrefijo() {
        return prefijo;
    }

    public static String getMensajeActivado() {
        return mensajeActivado;
    }

    public static String getMensajeDesactivado() {
        return mensajeDesactivado;
    }

    public static String getDeveloper() {
        return developer;
    }

    public static String getTitulo() {
        return titulo;
    }

    public static String getVidas() {
        return vidas;
    }

    public static String getVidasRestantes() {
        return vidasRestantes;
    }

    public static String getBan() {
        return ban;
    }
}
