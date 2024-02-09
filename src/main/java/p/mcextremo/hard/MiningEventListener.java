package p.mcextremo.hard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningEventListener implements Listener {

    private final EventManager eventManager;

    public MiningEventListener(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        // Verificar si el jugador tiene un contador asociado
        int currentCount = eventManager.incrementCounter(player);

        // Verificar si se alcanzó un valor específico para activar un evento
        if (currentCount == 10) {
            eventManager.startRandomEvent(player);
        }
    }
}

