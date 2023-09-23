package vdatta.us.danielbox.mines;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

import static vdatta.us.danielbox.util.utilities.BaseUtils.stringToLocation;

public class Mine {

    @Getter
    @Setter
    private String mineName;
    @Getter
    @Setter
    private Location pos1;
    @Getter
    @Setter
    private Location pos2;
    @Getter
    @Setter
    private List<Material> mineMaterials;

    public Mine(String mineName, Location pos1, Location pos2, List<Material> materials) {
        this.mineName = mineName;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.mineMaterials = materials;
    }

    public Mine(String mineName, String pos1, String pos2, Material[] materials) {
        this.mineName = mineName;
        this.pos1 = stringToLocation(pos1);
        this.pos2 = stringToLocation(pos2);
        this.mineMaterials = List.of(materials);
    }
}