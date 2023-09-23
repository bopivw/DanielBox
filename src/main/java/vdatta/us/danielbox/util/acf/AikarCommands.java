package vdatta.us.danielbox.util.acf;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.BukkitLocales;
import co.aikar.commands.Locales;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class AikarCommands {
    @Getter public BukkitCommandManager manager;
    public JavaPlugin instance;

    public AikarCommands(JavaPlugin plugin) {
        this.instance = plugin;
        this.manager = new BukkitCommandManager(plugin);
    }

    public void locale(){
        BukkitLocales locales = manager.getLocales();
        locales.addMessageBundle("daniel", Locales.SPANISH);
        locales.setDefaultLocale(Locales.SPANISH);
    }
    public void registerCommand(BaseCommand baseCommand){
        manager.registerCommand(baseCommand);
    }
}
