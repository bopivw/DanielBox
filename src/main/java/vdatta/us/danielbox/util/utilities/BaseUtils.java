package vdatta.us.danielbox.util.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static vdatta.us.danielbox.util.messaging.MessageComponent.format;

public class BaseUtils {


    public static Location stringToLocation(String locationString) {

        World world = Bukkit.getWorlds().get(0);
        String[] parts = locationString.split(" ");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Incorrect location format. Must be 'X,Y,Z'.");
        }

        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);

        if (world == null) {
            throw new IllegalArgumentException("World '" + world + "' not found.");
        }

        if (parts.length > 3) {
            float yaw = Float.parseFloat(parts[3]);
            float pitch = parts.length > 4 ? Float.parseFloat(parts[4]) : 0.0f;
            return new Location(world, x, y, z, yaw, pitch);
        } else {
            return new Location(world, x, y, z);
        }
    }

    public static void onlinePlayers(Consumer<Player> consumer) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            consumer.accept(player);
        }
    }


    public static String formatTime(int seconds) {
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    public static List<String> formatList(List<String> list) {
        List<String> formatted = new ArrayList<>();
        for (String s : list) {
            formatted.add(format(s));
        }
        return formatted;
    }

    public static List<String> formatList(Player player, List<String> list) {
        List<String> formatted = new ArrayList<>();
        for (String s : list) {
            formatted.add(format(player, s));
        }
        return formatted;
    }
}