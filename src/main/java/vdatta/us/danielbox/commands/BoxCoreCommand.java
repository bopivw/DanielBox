package vdatta.us.danielbox.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import vdatta.us.danielbox.DanielBox;
import vdatta.us.danielbox.util.configuration.Configuration;

import static vdatta.us.danielbox.util.messaging.MessageComponent.format;


@CommandPermission("danielbox.operator")
@CommandAlias("boxcore|core|danielbox")
public class BoxCoreCommand extends BaseCommand {

    @Default
    public void defaultCore(CommandSender sender){
        sender.sendMessage(format("&c&lERROR: &cIntenta utilizar un subcomando: (reload/scoreboard/etc)"));
    }
    @Subcommand("reload")
    public void reloadCore(CommandSender sender){
        for (Configuration configuration : DanielBox.getConfigurationManager().configurationList()) configuration.reload();
        sender.sendMessage(format("&e&lDanielCore &8» &aHas refrezcado todas las configuraciónes."));
    }
}
