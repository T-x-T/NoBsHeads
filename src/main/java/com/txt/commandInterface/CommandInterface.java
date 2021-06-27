package com.txt.commandInterface;

import java.util.ArrayList;
import java.util.List;

import com.txt.nobsheadsplugin.HeadFactory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import net.kyori.adventure.text.Component;

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

    if(args.length == 1 || args[1].length() < 1) {
      return false;
    }

    ItemStack itemStack = HeadFactory.getHeadByName(args[1]);

    System.out.println(itemStack.serialize().toString());
    

    
    Merchant merchant = Bukkit.createMerchant(Component.text("NoBsHeads"));

    List<MerchantRecipe> recipes = new ArrayList<>();
    MerchantRecipe recipe = new MerchantRecipe(itemStack, 0, 99999, false, 0, 10, true);
    recipe.addIngredient(new ItemStack(Material.DIAMOND));
    recipes.add(recipe);
    merchant.setRecipes(recipes);

    playerSender.openMerchant(merchant, true);

    return true;
  }
}