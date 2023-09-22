 package vdatta.us.danielbox.util.task;

 import org.bukkit.Bukkit;
 import org.bukkit.plugin.java.JavaPlugin;
 import studio.soarinng.com.danielstaff.DanielStaff;

 public class Task {


     private JavaPlugin plugin = DanielStaff.getInstance();
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