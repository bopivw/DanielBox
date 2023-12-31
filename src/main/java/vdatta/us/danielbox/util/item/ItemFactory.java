package vdatta.us.danielbox.util.item;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import vdatta.us.danielbox.DanielBox;

import java.lang.reflect.Field;
import java.util.*;

import static vdatta.us.danielbox.util.messaging.MessageComponent.format;
import static vdatta.us.danielbox.util.utilities.BaseUtils.formatList;

public class ItemFactory implements Listener {

    private JavaPlugin plugin = DanielBox.getInstance();
    private String displayName;
    private List<String> lore;
    private Material material;

    public ItemFactory() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public ItemFactory(Material material, String displayName, String... lore) {
        this.displayName = displayName;
        this.lore = new ArrayList<>(Arrays.asList(lore));
        this.material = material;
    }

    public ItemFactory(Material material, String displayName, ArrayList<String> lore) {
        this.displayName = displayName;
        this.lore = new ArrayList<>(lore);
        this.material = material;
    }


    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        itemMeta.setLore(formatList(lore));
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack buildEnchants(int power,Enchantment ... enchantments) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        itemMeta.setLore(formatList(lore));
        itemMeta.setUnbreakable(true);
        for (Enchantment enchant : enchantments){
            itemMeta.addEnchant(enchant,power,true);
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack buildCustomModelData(int CustomModelData) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        itemMeta.setLore(formatList(lore));
        itemMeta.setCustomModelData(CustomModelData);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack build(int count) {
        ItemStack itemStack = new ItemStack(material, count);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        itemMeta.setLore(formatList(lore));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack buildLeather(Color color) {
        ItemStack itemStack = new ItemStack(material);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setLore(formatList(lore));
        itemMeta.setColor(color);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    public ItemStack buildPlayerHead(String playerName) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
        skullMeta.setDisplayName(format(displayName));
        skullMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        skullMeta.setLore(formatList(lore));

        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = new ArrayList<>(lore);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ItemStack buildPlayerHeadTexture(String textureUrl) {
        textureUrl = "http://textures.minecraft.net/texture/" + textureUrl;
        ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        String base64Texture = getBase64FromTextureUrl(textureUrl);
        profile.getProperties().put("textures", new Property("textures", base64Texture));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        skullMeta.setDisplayName(format(displayName));
        skullMeta.setLore(formatList(lore));

        skullItem.setItemMeta(skullMeta);
        return skullItem;
    }

    public String getBase64FromTextureUrl(String textureUrl) {
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + UUID.randomUUID().toString();
        String payload = "{\"textures\":{\"SKIN\":{\"url\":\"" + textureUrl + "\"}}}";

        byte[] encodedPayload = Base64.getEncoder().encode(payload.getBytes());
        String encodedPayloadString = new String(encodedPayload);

        return encodedPayloadString;
    }
}