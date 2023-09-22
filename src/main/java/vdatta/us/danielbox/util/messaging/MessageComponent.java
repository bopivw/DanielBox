package vdatta.us.danielbox.util.messaging;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageComponent {

    public static String format(String content) {
        content = ChatColor.translateAlternateColorCodes('&', content);
        content = hex(content);
        return content;
    }

    public static String format(Player player, String content) {
        content = PlaceholderAPI.setPlaceholders(player, content);
        content = ChatColor.translateAlternateColorCodes('&', content);
        content = hex(content);
        return content;
    }

    private static String hex(final String content) {
        if (content == null) {
            return "&#FFFFF";
        }

        final char colorChar = ChatColor.COLOR_CHAR;
        Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
        final Matcher matcher = HEX_PATTERN.matcher(content);
        final StringBuffer buffer = new StringBuffer(content.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }
}