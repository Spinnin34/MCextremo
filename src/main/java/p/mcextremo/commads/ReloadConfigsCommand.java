package p.mcextremo.commads;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadConfigsCommand implements CommandExecutor {

    private final ConfigReloadManager configReloadManager;

    public ReloadConfigsCommand(ConfigReloadManager configReloadManager) {
        this.configReloadManager = configReloadManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("reloadconfigs")) {
            // Verificar si el jugador tiene permisos para ejecutar este comando

            // Recargar las configuraciones
            configReloadManager.loadConfigs();
            sender.sendMessage("Configuraciones cargadas correctamente.");
            return true;
        }
        return false;
    }
}


