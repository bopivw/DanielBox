package vdatta.us.danielbox.util.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import studio.soarinng.com.danielstaff.DanielStaff;
import studio.soarinng.com.danielstaff.util.types.TimerType;

import static studio.soarinng.com.danielstaff.util.messaging.MessageComponent.format;
import static studio.soarinng.com.danielstaff.util.utilities.BaseUtils.formatTime;

public class Countdown {

    private final JavaPlugin plugin = DanielStaff.getInstance();
    private final int time;
    private final TimerType type;


    private String title;
    private String subtitle;
    private String message;

    public Countdown(int time, TimerType type) {
        this.time = time;
        this.type = type;
    }

    public Countdown(int time, TimerType type, String title, String subtitle) {
        this.time = time;
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
    }

    public Countdown(int time, TimerType type, String typeMessage) {
        this.time = time;
        this.type = type;
        this.message = typeMessage;
    }




    public void startCustom() {
        new BukkitRunnable() {
            int remainingTime = time;

            @Override
            public void run() {
                if (remainingTime <= 0) {
                    cancel();
                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 2);
                        switch (type) {
                            case TITLE:
                                player.sendTitle(format(title), format(subtitle.replace("{time}", String.valueOf(remainingTime))), 10, 20, 10);
                                break;
                            case ACTIONBAR:
                                player.sendActionBar(format(message.replace("{time}", formatTime(remainingTime))));
                                break;
                            case MESSAGE:
                                // Envía el mensaje
                                player.sendMessage(format(message.replace("{time}", String.valueOf(remainingTime))));
                                break;
                        }
                    }
                    remainingTime--;
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void start() {
        new BukkitRunnable() {
            int remainingTime = time;

            @Override
            public void run() {
                if (remainingTime <= 0) {
                    cancel();
                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON,1,2);
                        switch (type) {
                            case TITLE:
                                player.sendTitle("TuTitulo", ChatColor.GREEN + "Tiempo restante: " + remainingTime, 10, 20, 10);
                                break;
                            case ACTIONBAR:
                                player.sendActionBar(format("&a"+formatTime(remainingTime)));
                                break;
                            case MESSAGE:
                                // Envía el mensaje
                                player.sendMessage(format("&eEl contador finaliza en &c{time} segundos&e.").replace("{time}", String.valueOf(remainingTime)));
                                break;
                        }
                    }
                    remainingTime--;
                }
            }
        }.runTaskTimer(plugin, 0, 20); // Ejecuta la tarea cada segundo (20 ticks)
    }
}