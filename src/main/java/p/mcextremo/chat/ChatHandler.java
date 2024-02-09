package p.mcextremo.chat;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import p.mcextremo.MCextremo;
import p.mcextremo.events.vidas;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatHandler implements Listener {

    private vidas vidasManager;
    private FileConfiguration config;
    private JavaPlugin plugin;
    private Map<Character, String> fontMappings;
    private boolean chatEnabled;
    private boolean chatFormatEnabled;
    private String heartSymbol;
    private String playerPrefix1;
    private String playerPrefix2;
    private String playerSuffix;

    public ChatHandler(vidas vidasManager, JavaPlugin plugin) {
        this.vidasManager = vidasManager;
        this.plugin = plugin;
        this.fontMappings = createFontMappings();

        reloadConfig();
    }

    public void reloadConfig() {
        // Guardar el archivo de configuración predeterminado si no existe
        saveDefaultConfig();

        // Cargar configuración desde el archivo
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "chat.yml"));

        // Obtener valores de la configuración
        this.chatEnabled = config.getBoolean("enabled", true);
        this.chatFormatEnabled = config.getBoolean("formato", true);
        this.playerPrefix1 = config.getString("playerPrefix1", "§f[§x§F§B§0§0§7§1❤");
        this.playerPrefix2 = config.getString("playerPrefix2", "§f] ");
        this.playerSuffix = config.getString("playerSuffix", "§7»§x§9§E§F§3§F§B");


    }

    public void saveConfig() {
        // Guardar configuración en el archivo
        try {
            config.save(new File(plugin.getDataFolder(), "chat.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public boolean isChatFormatEnabled() {
        return chatFormatEnabled;
    }

    public String getPlayerPrefix1() {
        return playerPrefix1;
    }

    public String getPlayerPrefix2() {
        return playerPrefix2;
    }

    public String getPlayerSuffix() {
        return playerSuffix;
    }

    // Puedes añadir métodos adicionales según tus necesidades

    private void saveDefaultConfig() {
        File configFile = new File(plugin.getDataFolder(), "chat.yml");
        if (!configFile.exists()) {
            plugin.saveResource("chat.yml", false);
        }
    }



    private Map<Character, String> createFontMappings() {
        // Define aquí tus mapeos de letra a letra con la fuente que deseas
        Map<Character, String> mappings = new HashMap<>();

        // Mapeos para letras minúsculas
        mappings.put('a', "ᴀ");
        mappings.put('b', "ʙ");
        mappings.put('c', "ᴄ");
        mappings.put('d', "ᴅ");
        mappings.put('e', "ᴇ");
        mappings.put('f', "ғ");
        mappings.put('g', "ɢ");
        mappings.put('h', "ʜ");
        mappings.put('i', "ɪ");
        mappings.put('j', "ᴊ");
        mappings.put('k', "ᴋ");
        mappings.put('l', "ʟ");
        mappings.put('m', "ᴍ");
        mappings.put('n', "ɴ");
        mappings.put('o', "ᴏ");
        mappings.put('p', "ᴘ");
        mappings.put('q', "ǫ");
        mappings.put('r', "ʀ");
        mappings.put('s', "s");
        mappings.put('t', "ᴛ");
        mappings.put('u', "ᴜ");
        mappings.put('v', "ᴠ");
        mappings.put('w', "ᴡ");
        mappings.put('x', "x");
        mappings.put('y', "ʏ");
        mappings.put('z', "ᴢ");
        // ... agrega más mapeos según tus necesidades

        // Mapeos para letras mayúsculas
        mappings.put('A', "ᴀ");
        mappings.put('B', "ʙ");
        mappings.put('C', "ᴄ");
        mappings.put('D', "ᴅ");
        mappings.put('E', "ᴇ");
        mappings.put('F', "ғ");
        mappings.put('G', "ɢ");
        mappings.put('H', "ʜ");
        mappings.put('I', "ɪ");
        mappings.put('J', "ᴊ");
        mappings.put('K', "ᴋ");
        mappings.put('L', "ʟ");
        mappings.put('M', "ᴍ");
        mappings.put('N', "ɴ");
        mappings.put('O', "ᴏ");
        mappings.put('P', "ᴘ");
        mappings.put('Q', "ǫ");
        mappings.put('R', "ʀ");
        mappings.put('S', "s");
        mappings.put('T', "ᴛ");
        mappings.put('U', "ᴜ");
        mappings.put('V', "ᴠ");
        mappings.put('W', "ᴡ");
        mappings.put('X', "x");
        mappings.put('Y', "ʏ");
        mappings.put('Z', "ᴢ");
        mappings.put('%', " ");
        // ... agrega más mapeos según tus necesidades

        return mappings;
    }


    private String parsePlaceholders(String input) {
        // Reemplazar los placeholders utilizando PlaceholderAPI si está disponible
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            input = PlaceholderAPI.setPlaceholders(null, input);
        }
        return input;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!chatEnabled) {
            // Chat deshabilitado, no hacer nada
            return;
        }

        Player jugador = event.getPlayer();
        int vidasRestantes = MCextremo.getVidasRestantes(jugador);

        // Formato del mensaje en el chat: [❤vidasRestantes] jugador: mensaje
        String mensajeOriginal = event.getMessage();
        StringBuilder mensajeFormateado = new StringBuilder("");

        if (chatFormatEnabled) {
            // Aplicar formato solo si está habilitado
            for (char letra : mensajeOriginal.toCharArray()) {
                // Reemplazar cada letra según tus mapeos de fuente
                String letraFormateada = fontMappings.getOrDefault(letra, String.valueOf(letra));
                mensajeFormateado.append(letraFormateada);
            }
        } else {
            mensajeFormateado.append(mensajeOriginal);
        }

        // Integración del placeholder %mcextremo_vidas_restantes%
        String mensajeFinal = playerPrefix1 + vidasRestantes + playerPrefix2 + jugador.getName() + " " + playerSuffix + " " + mensajeFormateado.toString();

        // Establecer el formato del evento de chat
        event.setFormat(ChatColor.translateAlternateColorCodes('&', mensajeFinal));
    }
}

