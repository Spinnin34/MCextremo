package p.mcextremo.events;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import p.mcextremo.Configuracion;
import p.mcextremo.MCextremo;
import p.mcextremo.mensaje.MessageUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class vidas implements Listener {

    private Map<UUID, Integer> vidas;

    private File configFile;
    private YamlConfiguration config;

    public vidas() {
        this.vidas = new HashMap<>();
        this.configFile = new File(MCextremo.getPlugin(MCextremo.class).getDataFolder(), "vidas.yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player jugador = event.getPlayer();
        UUID uuid = jugador.getUniqueId();

        // Cargar vidas desde el archivo
        if (config.contains(uuid.toString())) {
            int vidasGuardadas = config.getInt(uuid.toString());
            vidas.put(uuid, vidasGuardadas);
        } else {
            // Si no hay datos guardados, inicializar con 3 vidas
            vidas.put(uuid, 3);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player jugador = event.getEntity();
        UUID uuid = jugador.getUniqueId();

        int vidasRestantes = vidas.getOrDefault(uuid, 3);

        if (vidasRestantes > 0) {
            vidasRestantes--;
            vidas.put(uuid, vidasRestantes);
            jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getVidasRestantes()));
            jugador.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§0§0§7§1" + vidasRestantes + " §fvidas."));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getDeveloper()));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
            guardarVidas(uuid, vidasRestantes);


            // Mensaje global
            String mensajeGlobal = "§x§8§2§f§0§f§b" + jugador.getName() + "§x§F§B§4§7§4§7 ʜᴀ ᴘᴇʀᴅɪᴅᴏ ᴜɴᴀ ᴠɪᴅᴀ.";
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(" "));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(" "));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(mensajeGlobal));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage("ᴠɪᴅᴀs ʀᴇsᴛᴀɴᴛᴇs §x§F§B§0§0§7§1" + vidasRestantes));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(" "));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(Configuracion.getDeveloper()));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(" "));
        } else {
            // Lógica del baneo ejecutada sincrónicamente en el hilo principal
            String playerName = jugador.getName();
            String banCommand = "ban " + playerName;
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), banCommand);

            jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getBan()));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getDeveloper()));
            jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));

            String mensajeGlobal = "§x§8§2§f§0§f§b" + jugador.getName() + "§x§F§B§4§7§4§7 ʜᴀ ᴘᴇʀᴅɪᴅᴏ ᴛᴏᴅᴀs ʟᴀs ᴠɪᴅᴀs";

            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(" "));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(" "));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(mensajeGlobal));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage("§x§F§B§0§0§7§1ᴊᴜɢᴀᴅᴏʀ ᴅᴇsᴄᴀʟɪғɪᴄᴀᴅᴏ"));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(" "));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(Configuracion.getDeveloper()));
            Bukkit.broadcastMessage(MessageUtils.sendCenteredMessage(" "));
        }
    }



    private void guardarVidas(UUID uuid, int vidasRestantes) {
        // Manejar la concurrencia utilizando BukkitScheduler
        Bukkit.getScheduler().runTaskAsynchronously(MCextremo.getPlugin(MCextremo.class), () -> {
            config.set(uuid.toString(), vidasRestantes);

            try {
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Map<UUID, Integer> getVidas() {
        return vidas;
    }

    public int getVidasRestantes(Player player) {
        UUID uuid = player.getUniqueId();
        return vidas.getOrDefault(uuid, 3);
    }
}

