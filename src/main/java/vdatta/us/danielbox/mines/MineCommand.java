package vdatta.us.danielbox.mines;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import vdatta.us.danielbox.DanielBox;

import static vdatta.us.danielbox.util.messaging.MessageComponent.format;


@CommandPermission("danielbox.operator")
@CommandAlias("mine|caves|mines|coremine|minas|caves|cave|mina")
public class MineCommand extends BaseCommand {

    @Subcommand("create")
    public void createMina(Player player) {


        Mine mine = new Mine("MinaDeEjemplo","263 130 2","275 122 14", new Material[]{Material.STRIPPED_WARPED_HYPHAE});
        DanielBox.getInstance().getMineManager().saveMine(mine);

        player.sendMessage(format("&a¡REVISA!"));

    }
    @Subcommand("reset")
    public void reset(Player player) {
        MineManager mineManager = DanielBox.getInstance().getMineManager();
        Mine mine = mineManager.loadMine("MinaDeEjemplo");
        mineManager.resetMine(mine,player);

    }
}
