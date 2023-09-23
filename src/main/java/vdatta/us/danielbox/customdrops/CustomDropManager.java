package vdatta.us.danielbox.customdrops;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import vdatta.us.danielbox.util.configuration.Configuration;

public class CustomDropManager implements Listener {

    public Plugin plugin;
    public Configuration configuration;
    public ConfigurationSection section;

    public CustomDropManager(JavaPlugin instance, Configuration dropConfiguration) {
        this.plugin = instance;
        this.configuration = dropConfiguration;
        this.section = dropConfiguration.getConfigurationSection("customdrops-storage");
        if (section == null){
            dropConfiguration.createSection("customdrops-storage");
        }
    }


    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();

        if (section.contains(material.toString().toLowerCase())) {
            ItemStack itemStack = getCustomDrop(material);
            CustomDrop customDrop = new CustomDrop(material,itemStack);
            inventory.addItem(customDrop.getDrop());
            event.setCancelled(true);

        }
    }

    public ItemStack getCustomDrop(Material material){
        if (section.getString(String.valueOf(material)) != null){
            ItemStack itemStack = section.getItemStack(material+".drop");
            return itemStack;
        }
        return null;
    }
}
