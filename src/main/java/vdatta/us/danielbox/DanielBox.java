package vdatta.us.danielbox;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class DanielBox extends JavaPlugin {


    @Getter
    public static DanielBox instance;
    @Override
    public void onEnable() {
        instance = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
