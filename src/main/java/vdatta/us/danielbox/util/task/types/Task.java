package vdatta.us.danielbox.util.task.types;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Task {
    private JavaPlugin plugin;

    public Task(JavaPlugin javaPlugin){
        this.plugin = javaPlugin;
    }

    public Task(Runnable runnable) {
        this(runnable, 0L);
    }

    public Task(Runnable runnable, long delay) {
        if (plugin.isEnabled()) {
            int id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay);
        } else {
            runnable.run();
        }
    }
}
