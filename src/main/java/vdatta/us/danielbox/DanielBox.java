package vdatta.us.danielbox;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import vdatta.us.danielbox.commands.BoxCoreCommand;
import vdatta.us.danielbox.customdrops.CustomDrop;
import vdatta.us.danielbox.customdrops.CustomDropManager;
import vdatta.us.danielbox.mines.MineCommand;
import vdatta.us.danielbox.mines.MineManager;
import vdatta.us.danielbox.util.acf.AikarCommands;
import vdatta.us.danielbox.util.configuration.Configuration;
import vdatta.us.danielbox.util.configuration.ConfigurationManager;
import vdatta.us.danielbox.util.scoreboard.ScoreboardTask;

public final class DanielBox extends JavaPlugin {


    @Getter
    public static DanielBox instance;
    @Getter
    public static ConfigurationManager configurationManager;

    @Getter public static Configuration configuration;
    @Getter public static Configuration mineConfiguration;

    @Getter public ScoreboardTask scoreboardTask;
    @Getter public AikarCommands aikarCommands;
    @Getter public MineManager mineManager;
    @Getter public CustomDropManager customDropManager;

    @Override
    public void onEnable() {
        instance = this;
        aikarCommands = new AikarCommands(this);
        configurationManager = new ConfigurationManager(this);
        configuration = configurationManager.getConfig("config.yml");
        mineConfiguration = configurationManager.getConfig("mines.yml");
        mineManager = new MineManager(this, mineConfiguration);
        customDropManager = new CustomDropManager(this, mineConfiguration);
        scoreboardTask = new ScoreboardTask(this, configuration);

        aikarCommands.locale();
        aikarCommands.registerCommand(new BoxCoreCommand());
        aikarCommands.registerCommand(new MineCommand());
    }

    @Override
    public void onDisable() {

    }
}
