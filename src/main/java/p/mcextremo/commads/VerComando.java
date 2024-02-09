package p.mcextremo.commads;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import p.mcextremo.MCextremo;
import p.mcextremo.spectator.SpectatorManager;

public class VerComando implements CommandExecutor {

    private final MCextremo plugin;

    public VerComando(MCextremo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador.");
            return true;
        }

        Player player = (Player) sender;

        // Verificar si se proporcionó un jugador como argumento
        if (args.length >= 1) {
            String targetPlayerName = args[0];
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer != null && targetPlayer.isOnline()) {
                SpectatorManager.enterSpectatorMode(player, targetPlayer);
                return true;
            } else {
                player.sendMessage("El jugador especificado no está en línea.");
                return false;
            }
        } else {
            player.sendMessage("Uso correcto: /ver <jugador> [jugador]");
            return false;
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SpectatorManager.exitSpectatorMode(player);
    }
}
