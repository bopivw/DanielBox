package vdatta.us.danielbox.util.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;
import vdatta.us.danielbox.util.configuration.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static vdatta.us.danielbox.util.messaging.MessageComponent.format;


public class ScoreboardTask {
    private final JavaPlugin plugin;
    private final Configuration config;
    private Scoreboard scoreboard;
    private Objective objective;
    private final Map<Integer, Team> teams = new HashMap<>();
    private BukkitTask taskManager;

    public ScoreboardTask(JavaPlugin instance, Configuration configuration) {
        plugin = instance;
        config = configuration;
    }

    public void startTask() {
        if (taskManager != null && !taskManager.isCancelled()) {
            taskManager.cancel();
        }

        taskManager = new BukkitRunnable() {
            @Override
            public void run() {
                if (config.getBoolean("scoreboards.enabled")) {
                    Bukkit.getOnlinePlayers().forEach(ScoreboardTask.this::createScoreboard);
                }
            }
        }.runTaskTimer(plugin, 0, 20L);
    }

    public void createScoreboard(Player player) {
        clearScoreboard(player);

        String title = config.getString("scoreboards." + getScoreboard() + ".title");
        List<String> lines = config.getStringList("scoreboards." + getScoreboard() + ".lines");
        ScoreboardManager scoreboardTask = Bukkit.getScoreboardManager();
        scoreboard = scoreboardTask.getNewScoreboard();
        objective = scoreboard.registerNewObjective("example", "dummy", format(player, title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        IntStream.range(0, lines.size())
                .forEachOrdered(i -> addLine(lines.get(i), lines.size() - i, player));

        player.setScoreboard(scoreboard);
    }

    private void clearScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    private void addLine(String text, int score, Player player) {
        String coloredText = format(player, text);
        Team team = scoreboard.registerNewTeam("line" + score);
        team.addEntry(String.valueOf(ChatColor.values()[score]));
        team.setPrefix(coloredText);
        objective.getScore(String.valueOf(ChatColor.values()[score])).setScore(score);
        teams.put(score, team);
    }

    public String getScoreboard() {
        return config.getString("scoreboards.score");
    }
}