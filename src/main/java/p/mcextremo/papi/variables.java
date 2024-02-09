package p.mcextremo.papi;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import p.mcextremo.MCextremo;
import p.mcextremo.events.vidas;

import java.util.HashMap;
import java.util.UUID;
public class variables extends PlaceholderExpansion {

    private MCextremo plugin;
    private long cooldownEndTime = 0;
    public variables(MCextremo plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "mcextremo";
    }

    @Override
    public String getAuthor() {
        return "spinnin";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    // Este método se llama cuando un jugador solicita un placeholder
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        // Verificar el identificador y proporcionar el valor correspondiente
        if (identifier.equals("vidas_restantes")) {
            return String.valueOf(plugin.getVidasRestantes(player));
        } else if (identifier.equals("pvp_activo")) {
            return plugin.isPvPActivo() ? "§x§A§2§F§B§7§Eᴀᴄᴛɪᴠᴀᴅᴏ" : "§x§F§B§5§D§5§Dᴅᴇꜱᴀᴄᴛɪᴠᴀᴅᴏ";
        } else if (identifier.equals("contador_eventos")) {
            return String.valueOf(plugin.getEventManager().getEventCounter(player));
        } else if (identifier.equals("evento_actual")) {
            String eventName = plugin.getEventManager().getCurrentEventName();
            return (eventName != null) ? eventName : "No hay evento activo";
        } else if (identifier.equals("tiempo_pvp")) {
            if (isCooldownActive()) {
                return formatTime((int) ((cooldownEndTime - System.currentTimeMillis()) / 1000));
            } else {
                setGlobalCooldown(); // Establecer el cooldown global
                int timeUntilNextEvent = plugin.getEventManager().getTimeUntilNextEvent();
                return formatTime(timeUntilNextEvent);
            }
        }

        return null;
    }

    // Método para verificar si el cooldown global está activo
    private boolean isCooldownActive() {
        return System.currentTimeMillis() < cooldownEndTime;
    }

    // Método para establecer el cooldown global
    private void setGlobalCooldown() {
        cooldownEndTime = System.currentTimeMillis() + (60 * 60 * 1000); // 1 hora de cooldown
    }

    // Método para formatear el tiempo en "mm:ss"
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}

