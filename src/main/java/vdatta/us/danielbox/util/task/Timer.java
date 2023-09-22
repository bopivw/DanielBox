package vdatta.us.danielbox.util.task;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import studio.soarinng.com.danielstaff.DanielStaff;

import static studio.soarinng.com.danielstaff.util.messaging.MessageComponent.format;
import static studio.soarinng.com.danielstaff.util.utilities.BaseUtils.formatTime;

public class Timer {
    public static BossBar bossBar;
    public static int taskId;
    private static int timem = 1;
    private Plugin instance = DanielStaff.getInstance();



    public void create(BarColor barColor, BarStyle barStyle, String message, int time) {
        create(barColor, barStyle, message, time, null);
    }

    public void create(BarColor barColor, BarStyle barStyle, String message, int time, Runnable runnable) {
        unregister();
        timem = time;
        final String[] title = {""};
        bossBar = Bukkit.createBossBar(title[0], barColor, barStyle);
        bossBar.setProgress(1.0);
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            timem--;
            title[0] = format(message).replace("%time%", formatTime(timem));
            double progress = (double) timem / (time);
            bossBar.setProgress(progress);
            bossBar.setTitle(title[0]);

            for (Player player : Bukkit.getOnlinePlayers()) {
                bossBar.addPlayer(player);
            }

            if (progress <= 0.0) {
                bossBar.removeAll();
            }
            if (timem <= 0) {
                bossBar.removeAll();
                Bukkit.getScheduler().cancelTask(taskId);
                if (runnable != null) {
                    runnable.run();
                }
            }
        }, 0L, 20L);
    }

    public static void unregister() {
        if (bossBar != null) {
            if (taskId != -1) {
                Bukkit.getScheduler().cancelTask(taskId);
                taskId = -1;
            }
            bossBar.removeAll();
            bossBar = null;
        }
    }
}