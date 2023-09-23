package vdatta.us.danielbox.customdrops;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("ALL")
public class CustomDrop {

    @Getter
    public Material material;

    @Getter
    public ItemStack drop;

    public CustomDrop(Material material, ItemStack customDrop){
        this.material = material;
        this.drop = customDrop;
    }
}
