package com.txt.craftingInterface;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftingInterface {
  public CraftingInterface(JavaPlugin plugin) {
    ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
    SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
    meta.setOwner("MHF_apple"); //Must use deprecated API to access player heads from players who have never been online
    itemStack.setItemMeta(meta);
    
    ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, "MHF_apple"), itemStack);
    recipe.addIngredient(Material.APPLE);

    Bukkit.broadcastMessage(String.valueOf(Bukkit.addRecipe(recipe)));
  }
}