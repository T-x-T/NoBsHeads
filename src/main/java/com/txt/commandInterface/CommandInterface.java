package com.txt.commandInterface;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.SkullMeta;

public class CommandInterface implements CommandExecutor {
  public CommandInterface() {

  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    switch(args[0]) {
      case "buy": {
        return buyCommand(sender, command, label, args);
      }
      default: {
        return false;
      }
    }
  }

  private boolean buyCommand(CommandSender sender, Command command, String label, String[] args) {
    if(!(sender instanceof Player)) {
      sender.sendMessage("This command is only for players and not for ghosts in the shell!");
      return true;
    }
    
    Player playerSender = (Player) sender;
    ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
    SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
    meta.setOwner(args[1]); //Must use deprecated API to access player heads from players who have never been online
    itemStack.setItemMeta(meta);
    try {
      itemStack.setAmount(Integer.parseInt(args[2]));
    } catch(Exception e) {
      itemStack.setAmount(1);
    }

    //playerSender.getLocation().getWorld().dropItem(playerSender.getLocation(), itemStack);

    Merchant merchant = Bukkit.createMerchant("NoBsHeads");
    List<MerchantRecipe> recipes = new ArrayList<>();
    MerchantRecipe recipe = new MerchantRecipe(itemStack, 0, 99999, false, 0, 10, true);
    recipe.addIngredient(new ItemStack(Material.DIAMOND));
    recipes.add(recipe);
    merchant.setRecipes(recipes);
    playerSender.openMerchant(merchant, true);
    return false;
  }
}
