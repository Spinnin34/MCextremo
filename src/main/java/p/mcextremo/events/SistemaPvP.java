package p.mcextremo.events;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import p.mcextremo.Configuracion;
import p.mcextremo.MCextremo;
import p.mcextremo.mensaje.MessageUtils;

import java.util.Random;

public class SistemaPvP implements Listener {

    private MCextremo plugin;
    private boolean pvpActivo;
    private int duracionPvP; // Duración en segundos
    private int tiempoRestante;






    public SistemaPvP(MCextremo plugin, int duracionPvP) {
        this.plugin = plugin;
        this.pvpActivo = false;
        this.duracionPvP = duracionPvP;
        this.tiempoRestante = duracionPvP;

        Configuracion.cargarYConfigurar(plugin);

        // Iniciar el sistema de PvP
        iniciarSistemaPvP();
    }








    private void iniciarSistemaPvP() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (tiempoRestante > 0) {
                    tiempoRestante--;
                } else {
                    pvpActivo = !pvpActivo;
                    plugin.setPvPActivo(pvpActivo);

                    if (pvpActivo) {
                        activarPvP();
                        plugin.setPvPActivo(true);

                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getMensajeActivado()));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getDeveloper()));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                        enviarTitulosGlobal(Configuracion.getTitulo(), Configuracion.getMensajeActivado());
                    } else {
                        desactivarPvP();
                        plugin.setPvPActivo(false);

                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getMensajeDesactivado()));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getDeveloper()));
                        enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                        enviarTitulosGlobal(Configuracion.getTitulo(), Configuracion.getMensajeDesactivado());
                    }

                    // Reiniciar el temporizador para la próxima activación/desactivación
                    tiempoRestante = duracionPvP;
                }
            }
        }.runTaskTimer(plugin, 0, 20); // Se ejecuta cada segundo
    }

    private void activarPvP() {
        for (World world : Bukkit.getWorlds()) {
            world.setPVP(true);
        }
    }

    private void desactivarPvP() {
        for (World world : Bukkit.getWorlds()) {
            world.setPVP(false);
        }
    }

    private boolean generarEventoPvP() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private void enviarMensajeGlobal(String mensaje) {
        Bukkit.broadcastMessage(mensaje);
        String.valueOf(pvpActivo);

    }

    private void enviarTitulosGlobal(String titulo, String subtitulo) {
        // Enviar títulos a todos los jugadores en el servidor
        for (Player jugador : Bukkit.getOnlinePlayers()) {
            jugador.sendTitle(titulo, subtitulo, 10, 70, 20);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Puedes agregar lógica adicional relacionada con el movimiento del jugador
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String comando = event.getMessage().toLowerCase();
        Player jugador = event.getPlayer();

        if (comando.startsWith("/pvp on")) {
            if (jugador.hasPermission("mcextremo.pvp.on")) {

                enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getMensajeActivado()));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getDeveloper()));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                enviarTitulosGlobal(Configuracion.getTitulo(), Configuracion.getMensajeActivado());

                pvpActivo = true;
                plugin.setPvPActivo(true);
                event.setCancelled(true); // Evita que el jugador ejecute el comando
            } else {
                jugador.sendMessage(ChatColor.RED + "No tienes permisos para activar el PvP.");
            }
        } else if (comando.startsWith("/pvp off")) {
            if (jugador.hasPermission("mcextremo.pvp.off")) {

                enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getPrefijo()));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getMensajeDesactivado()));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage(Configuracion.getDeveloper()));
                enviarMensajeGlobal(MessageUtils.sendCenteredMessage("§c"));
                enviarTitulosGlobal(Configuracion.getTitulo(), Configuracion.getMensajeDesactivado());

                pvpActivo = false;
                plugin.setPvPActivo(false);
                event.setCancelled(true); // Evita que el jugador ejecute el comando
            } else {
                jugador.sendMessage(ChatColor.RED + "No tienes permisos para desactivar el PvP.");
            }
        }
    }
}



