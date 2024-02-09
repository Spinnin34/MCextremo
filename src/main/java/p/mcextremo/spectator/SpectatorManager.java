package p.mcextremo.spectator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import p.mcextremo.MCextremo;

import java.util.Objects;

public class SpectatorManager {

    public static void enterSpectatorMode(Player spectator, Player target) {
        // Cambiar al modo espectador
        spectator.setGameMode(GameMode.SPECTATOR);

        // Teletransportar al espectador al jugador objetivo
        spectator.teleport(target);

        // Ocultar al espectador del resto de los jugadores
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.equals(spectator)) {
                onlinePlayer.hidePlayer(Objects.requireNonNull(spectator));
            }
        }
    }

    public static void exitSpectatorMode(Player spectator) {
        // Cambiar al modo supervivencia (o el modo que prefieras)
        spectator.setGameMode(GameMode.SURVIVAL);

        // Mostrar al espectador nuevamente a todos los jugadores
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(Objects.requireNonNull(spectator));
        }
    }
}
