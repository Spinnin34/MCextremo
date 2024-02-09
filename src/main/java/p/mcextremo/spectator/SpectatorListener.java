package p.mcextremo.spectator;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import p.mcextremo.MCextremo;

public class SpectatorListener implements Listener {

    private final MCextremo plugin;

    public SpectatorListener(MCextremo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Verificar si el jugador que sale estaba en modo espectador y manejar según tu lógica
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.SPECTATOR) {
            // Puedes realizar acciones específicas cuando un jugador en modo espectador sale del juego
            SpectatorManager.exitSpectatorMode(player);
        }
    }
}

