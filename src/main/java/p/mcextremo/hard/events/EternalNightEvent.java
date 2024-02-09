package p.mcextremo.hard.events;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EternalNightEvent extends BukkitRunnable {

    private final JavaPlugin plugin;
    private final World world;
    private final boolean originalStormState;
    private final boolean originalThunderingState;
    private int countdown;

    public EternalNightEvent(JavaPlugin plugin, World world) {
        this.plugin = plugin;
        this.world = world;
        this.originalStormState = world.hasStorm();
        this.originalThunderingState = world.isThundering();
    }

    @Override
    public void run() {
        // Activar la Noche Eterna
        world.setTime(14000);  // Establecer el tiempo a la medianoche
        world.setStorm(false);  // Detener la lluvia si está ocurriendo

        countdown--;

        String timeRemaining = String.format("%02d:%02d", countdown / 60, countdown % 60);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("¡Noche Eterna activada! Tiempo restante: " + timeRemaining);
        }

        // Programar la finalización del evento después de 30 minutos
        new BukkitRunnable() {
            @Override
            public void run() {
                // Restablecer el tiempo y las condiciones climáticas al estado anterior
                world.setFullTime(world.getFullTime());  // Restablecer el tiempo al estado original
                world.setStorm(originalStormState);      // Restablecer el estado de la tormenta
                world.setThundering(originalThunderingState);  // Restablecer el estado de tormenta eléctrica si estaba activa
            }
        }.runTaskLater(plugin, 30 * 60 * 20); // 30 minutos en ticks (20 ticks/segundo)
    }
}
