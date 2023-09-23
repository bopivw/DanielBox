package vdatta.us.danielbox.util.task;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import vdatta.us.danielbox.util.task.types.TimerType;

import static vdatta.us.danielbox.util.messaging.MessageComponent.format;
import static vdatta.us.danielbox.util.utilities.BaseUtils.formatTime;

public class CountdownTask {
    private JavaPlugin plugin;
    private long time;
    private TimerType type;
    private String title;
    private String subtitle;
    private String message;

    private static final int TITLE_FADE_IN = 10;
    private static final int TITLE_STAY = 20;
    private static final int TITLE_FADE_OUT = 10;

    public CountdownTask(JavaPlugin javaPlugin){
        this.plugin = javaPlugin;
    }
    public CountdownTask(long time, TimerType type) {
        this(time, type, "", "");
    }

    public CountdownTask(long time, TimerType type, String title, String subtitle) {
        this(time, type, title, subtitle, "");
    }

    public CountdownTask(long time, TimerType type, String title, String subtitle, String message) {
        this.time = time;
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.message = message;
    }

    public void start() {
        new BukkitRunnable() {
            long remainingTime = time;

            @Override
            public void run() {
                if (remainingTime <= 0) {
                    cancel();
                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 2);
                        switch (type) {
                            case TITLE:
                                player.sendTitle(format(title), format(subtitle.replace("{time}", String.valueOf(remainingTime))),
                                        TITLE_FADE_IN, TITLE_STAY, TITLE_FADE_OUT);
                                break;
                            case ACTIONBAR:
                                player.sendActionBar(format("&a" + formatTime(Math.toIntExact(remainingTime))));
                                break;
                            case MESSAGE:
                                player.sendMessage(format(message.replace("{time}", String.valueOf(remainingTime))));
                                break;
                        }
                    }
                    remainingTime--;
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
