package com.txt.nobsheadsplugin;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

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

  @SuppressWarnings("unchecked")
  public static boolean isHeadPurchasable(JavaPlugin plugin, String nameToCheck) {
    ArrayList<String> list = (ArrayList<String>) plugin.getConfig().getList("trading.list");

    boolean contains = false;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equalsIgnoreCase(nameToCheck))
        contains = true;
    }

    if (plugin.getConfig().getString("trading.listMode").equals("deny")) {
      if (contains)
        return false;
    } else if (plugin.getConfig().getString("trading.listMode").equals("allow")) {
      if (!contains)
        return false;
    }

    return true;
  }
}
