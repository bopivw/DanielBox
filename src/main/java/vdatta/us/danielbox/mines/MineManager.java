package vdatta.us.danielbox.mines;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import vdatta.us.danielbox.util.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MineManager {

    private final Plugin plugin;
    private final Configuration config;

    public MineManager(Plugin plugin, Configuration configuration) {
        this.plugin = plugin;
        this.config = configuration;
    }

    public void saveMine(Mine mine) {
        ConfigurationSection mineSection = config.createSection("mine-storage." + mine.getMineName());
        mineSection.set("name", mine.getMineName());

        ConfigurationSection locationSection = mineSection.createSection("locations");
        locationSection.set("pos1", mine.getPos1());
        locationSection.set("pos2", mine.getPos2());

        List<String> materialNames = new ArrayList<>();
        for (Material material : mine.getMineMaterials()) {
            materialNames.add(material.name());
        }
        mineSection.set("materials", materialNames);
        config.safeSave();
    }

    public Mine loadMine(String mineName) {
        ConfigurationSection mineSection = config.getConfigurationSection("mine-storage." + mineName);
        if (mineSection == null) {
            return null;
        }

        String name = mineSection.getString("name");
        ConfigurationSection locationSection = mineSection.getConfigurationSection("locations");
        Location pos1 = locationSection.getLocation("pos1");
        Location pos2 = locationSection.getLocation("pos2");

        List<String> materialNames = mineSection.getStringList("materials");
        List<Material> materials = new ArrayList<>();

        for (String materialName : materialNames) materials.add(Material.valueOf(materialName));

        return new Mine(name, pos1, pos2, materials);

    }

    public void resetMine(Mine mine, Player player) {
        Location pos1 = mine.getPos1();
        Location pos2 = mine.getPos2();
        List<Material> materials = mine.getMineMaterials();
        World world = pos1.getWorld();

        if (pos1.getWorld() == null || pos2.getWorld() == null || !pos1.getWorld().equals(pos2.getWorld())) {
            player.sendMessage("Las ubicaciones no son válidas o no están en el mismo mundo.");
            return;
        }

        int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());

        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());


        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Material bloque = materials.get((int) (Math.random() * materials.size()));
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(bloque);
                }
            }
        }
    }
}