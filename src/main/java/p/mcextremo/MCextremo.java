package p.mcextremo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import p.mcextremo.chat.ChatHandler;
import p.mcextremo.commads.ConfigReloadManager;
import p.mcextremo.commads.ReloadConfigsCommand;
import p.mcextremo.events.SistemaPvP;
import p.mcextremo.events.vidas;
import p.mcextremo.hard.EventManager;
import p.mcextremo.mensaje.MessageUtils;
import p.mcextremo.papi.variables;
import p.mcextremo.spectator.SpectatorListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class MCextremo extends JavaPlugin {
    private static vidas vidasManager;
    public String latestversion;
    PluginDescriptionFile pdffile = getDescription();
    public String version = pdffile.getVersion();
    private EventManager eventManager;

    private boolean pvpActivo = false;
    private Configuracion config;
    private ChatHandler chatHandler;
    private ConfigReloadManager configReloadManager;


    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File ajustesFile = new File(getDataFolder(), "config.yml");
        if (!ajustesFile.exists()) {
            saveResource("config.yml", false);
        }

        // Cargar configuración 'ajustes.yml'
        Configuracion.cargarYConfigurar(this);

        // Configuración 'chat.yml'
        File chatFile = new File(getDataFolder(), "chat.yml");
        if (!chatFile.exists()) {
            saveResource("chat.yml", false);
        }

        configReloadManager = new ConfigReloadManager(this);


        this.vidasManager = new vidas();
        chatHandler = new ChatHandler(new vidas(), this);
        this.saveDefaultConfig();
        reloadConfig();
        updateChecker();

        eventManager = new EventManager(this);
        getServer().getPluginManager().registerEvents(new SpectatorListener(this), this);
        getServer().getPluginManager().registerEvents(vidasManager, this);
        this.getServer().getPluginManager().registerEvents(new SistemaPvP(this, 3600), this); // 3600 segundos (1 hora) de duración del PvP
        getServer().getPluginManager().registerEvents(chatHandler, this);


        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new variables(this).register();
        } else {
            getLogger().warning("PlaceholderAPI no encontrado. Algunos placeholders no funcionarán.");
        }

        // Plugin startup logic

    }
    public static int getVidasRestantes(Player player) {
        return vidasManager.getVidasRestantes(player);
    }

    public boolean isPvPActivo() {
        return pvpActivo;
    }

    public void setPvPActivo(boolean nuevoEstadoPvP) {
        pvpActivo = nuevoEstadoPvP;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vidas")) {
            if (sender instanceof Player) {
                Player jugador = (Player) sender;
                int vidasRestantes = getVidasRestantes(jugador);
                jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
                jugador.sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
                jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
                jugador.sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getVidas() + " " +  vidasRestantes + "❤"));
                jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
                jugador.sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getDeveloper()));
                jugador.sendMessage(MessageUtils.sendCenteredMessage(" "));
                return true;
            }
        }
        return false;
    }
    public EventManager getEventManager() {
        return eventManager;
    }




    public void updateChecker(){
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "https://api.spigotmc.org/legacy/update.php?resource=114819").openConnection();
            int timed_out = 1250;
            con.setConnectTimeout(timed_out);
            con.setReadTimeout(timed_out);
            latestversion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (latestversion.length() <= 7) {
                if(!version.equals(latestversion)){
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage(" "));
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage(" "));
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage("§7Nueva version de Plugin descargala:"));
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage("§chttps://karmancos.42web.io/download/mcextremo.html"));
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage(" "));
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage("§fFrom Team Karmancos Studio"));
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage(" "));
                }
            }
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage(" "));
            Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
            Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage(" "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED +"Error while checking update.");
            Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage("§fFrom Team Karmancos Studio"));
            Bukkit.getConsoleSender().sendMessage(MessageUtils.sendCenteredMessage(" "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED +"Error while checking update.");
        }
    }
}
