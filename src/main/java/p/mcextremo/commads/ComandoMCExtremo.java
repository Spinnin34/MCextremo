package p.mcextremo.commads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import p.mcextremo.Configuracion;
import p.mcextremo.MCextremo;

public class ComandoMCExtremo implements CommandExecutor {
    private final JavaPlugin plugin;

    public ComandoMCExtremo(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("saveconfig")) {
            plugin.saveDefaultConfig();
            sender.sendMessage("Configuración guardada correctamente.");
            return true;
        }
        return false;
    }
}

