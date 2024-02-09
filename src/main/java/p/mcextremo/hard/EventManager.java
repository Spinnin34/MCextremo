package p.mcextremo.hard;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import p.mcextremo.hard.events.EternalNightEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EventManager {

    private final JavaPlugin plugin;
    private final Map<String, Integer> playerCounters = new HashMap<>();
    private String currentEventName;
    private int timeUntilNextEvent;
    private EternalNightEvent eternalNightEvent;  // Agrega esta línea

    public EventManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.currentEventName = null;
        this.timeUntilNextEvent = 0;
        this.eternalNightEvent = null;  // Agrega esta línea
    }

    public int incrementCounter(Player player) {
        String playerName = player.getName();
        playerCounters.putIfAbsent(playerName, 0);

        int currentCount = playerCounters.get(playerName) + 1;
        playerCounters.put(playerName, currentCount);
        return currentCount;
    }

    public void startRandomEvent(Player player) {
        // Lógica para determinar el evento aleatorio
        if (Math.random() < 0.5) {
            // Ejemplo: Iniciar el evento de Noche Eterna
            startEternalNightEvent(player.getWorld());
        } else {
            // Ejemplo: Iniciar otro tipo de evento (puedes añadir más casos aquí)
            startOtherRandomEvent(player);
        }
    }
    private void startOtherRandomEvent(Player player) {
        // Lógica para otro tipo de evento (puedes añadir más casos aquí)
        // Ejemplo: Iniciar un evento aleatorio diferente
        int randomNumber = (int) (Math.random() * 3);  // Generar un número aleatorio (ejemplo: 0, 1 o 2)

        switch (randomNumber) {
            case 0:
                // Lógica para el primer tipo de evento
                player.sendMessage("Iniciando el primer tipo de evento aleatorio.");
                // Aquí puedes realizar acciones específicas para este tipo de evento
                break;

            case 1:
                // Lógica para el segundo tipo de evento
                player.sendMessage("Iniciando el segundo tipo de evento aleatorio.");
                // Aquí puedes realizar acciones específicas para este tipo de evento
                break;

            case 2:
                // Lógica para el tercer tipo de evento
                player.sendMessage("Iniciando el tercer tipo de evento aleatorio.");
                // Aquí puedes realizar acciones específicas para este tipo de evento
                break;

            default:
                // Lógica por defecto si el número aleatorio no coincide con ningún caso (esto debería ser poco probable)
                player.sendMessage("Iniciando un evento aleatorio diferente.");
                // Aquí puedes realizar acciones genéricas para cualquier otro tipo de evento
                break;
        }
    }


    public int getEventCounter(Player player) {
        String playerName = player.getName();
        playerCounters.putIfAbsent(playerName, 0);
        return playerCounters.get(playerName);
    }

    public String getCurrentEventName() {
        return currentEventName;
    }

    public int getTimeUntilNextEvent() {
        return timeUntilNextEvent;
    }




    private void startEternalNightEvent(World world) {
        // Verificar si ya hay un evento de Noche Eterna en progreso
        if (eternalNightEvent != null) {
            eternalNightEvent.cancel();
        }

        // Iniciar un nuevo evento de Noche Eterna
        eternalNightEvent = new EternalNightEvent(plugin, world);
        eternalNightEvent.runTaskTimer(plugin, 0L, 20L);
    }


    public boolean handleCommandEvent(CommandSender sender, Command cmd, String label, String[] args) {
        // Comando de prueba para iniciar un evento aleatorio (puedes modificarlo según tus necesidades)
        if (cmd.getName().equalsIgnoreCase("hard") && sender instanceof Player) {
            Player player = (Player) sender;
            startRandomEvent(player);
            return true;
        }
        return false;
    }
}

