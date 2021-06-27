package com.txt.nobsheadsplugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadFactory {

  public static ItemStack getHeadByName(String name) {
    return getHeadsByName(name, 1);
  }

  @SuppressWarnings("deprecation")
  public static ItemStack getHeadsByName(String name, int amount) {
    ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);

    SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
    meta.setOwner(name); //Must use deprecated API to access player heads from players who have never been online
    itemStack.setItemMeta(meta);
    
    itemStack.setAmount(amount);

    return itemStack;
  }
}
