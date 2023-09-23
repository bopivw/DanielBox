package vdatta.us.danielbox.util.task.types;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import vdatta.us.danielbox.DanielBox;

import java.util.concurrent.atomic.AtomicInteger;

import static vdatta.us.danielbox.util.messaging.MessageComponent.format;
import static vdatta.us.danielbox.util.utilities.BaseUtils.formatTime;

public class Timer {
    private static BossBar bossBar;
    private static final Plugin instance = DanielBox.getInstance();
    private static final long TICKS_PER_SECOND = 20L;
    private static final int MAX_PROGRESS = 100;

    public void create(BarColor barColor, BarStyle barStyle, String message, int time) {
        create(barColor, barStyle, message, time, null);
    }

    public void create(BarColor barColor, BarStyle barStyle, String message, int time, Runnable runnable) {
        unregister();

        AtomicInteger timem = new AtomicInteger(time);
        bossBar = Bukkit.createBossBar(format(message).replace("%time%", formatTime(timem.get())), barColor, barStyle);
        bossBar.setProgress(1.0);

        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            int remainingTime = timem.decrementAndGet();
            double progress = (double) remainingTime / time;
            bossBar.setProgress(Math.max(progress, 0.0));

            bossBar.setTitle(format(message).replace("%time%", formatTime(remainingTime)));

            for (Player player : Bukkit.getOnlinePlayers()) {
                bossBar.addPlayer(player);
            }

            if (remainingTime <= 0) {
                unregister();
                if (runnable != null) {
                    runnable.run();
                }
            }
        }, 0L, TICKS_PER_SECOND);

        for (Player player : Bukkit.getOnlinePlayers()) bossBar.addPlayer(player);
        bossBar.setVisible(true);
    }

    public static void unregister() {
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar.setVisible(false);
            bossBar = null;
        }
    }
}
