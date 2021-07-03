package com.txt.commandInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;

public class CommandInterface implements CommandExecutor {
  private HashMap<UUID, ArrayList<String>> purchaseHistory;
  private JavaPlugin plugin;

  private ItemStack tradeIngredient;
  private int tradeResultAmount;

  public CommandInterface(JavaPlugin plugin) {
    this.plugin = plugin;
    this.purchaseHistory = new HashMap<>();
    this.purchaseHistory = PurchaseHistoryPersistance.loadPurchaseHistory(plugin);
    this.savePurchaseHistoryPeriodically(2000);

    this.tradeIngredient = new ItemStack(Material.getMaterial(plugin.getConfig().getString("trading.ingredient")), plugin.getConfig().getInt("trading.ingredientAmount"));
    this.tradeResultAmount = this.plugin.getConfig().getInt("trading.resultAmount");
  }

  private void savePurchaseHistoryPeriodically(int everyTicks) {
    Bukkit.getScheduler().runTaskTimer(plugin, () -> PurchaseHistoryPersistance.savePurchaseHistoryAsyncIfPossible(plugin, purchaseHistory), everyTicks, everyTicks);
  }

  public void disable() {
    PurchaseHistoryPersistance.savePurchaseHistoryAsyncIfPossible(this.plugin, this.purchaseHistory);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(args.length < 1) {
      return false;
    }

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
    
    if (args.length > 1) {
      if(!HeadFactory.isHeadPurchasable(this.plugin, args[1])) {
        sender.sendMessage(Component.text("You are not allowed to buy this head"));
        return true;
      }
      
      ArrayList<String> purchaseList = new ArrayList<>();
      purchaseList.add(args[1]);

      if (!purchaseHistory.containsKey(playerSender.getUniqueId())) {
        purchaseHistory.put(playerSender.getUniqueId(), purchaseList);
      } else {
        purchaseHistory.get(playerSender.getUniqueId()).remove(args[1]);
        purchaseList.addAll(purchaseHistory.get(playerSender.getUniqueId()));
        purchaseHistory.put(playerSender.getUniqueId(), purchaseList);
      }
    } else if (!purchaseHistory.containsKey(playerSender.getUniqueId())) {
      playerSender.sendMessage(Component.text("You need to buy a head, before you can look at your purchase history"));
    }

    Merchant merchant = Bukkit.createMerchant(Component.text("NoBsHeads"));

    List<MerchantRecipe> recipes = new ArrayList<>();
    
    purchaseHistory.get(playerSender.getUniqueId()).forEach((name) -> {
      ItemStack itemStack = HeadFactory.getHeadsByName(name, this.tradeResultAmount);
      MerchantRecipe recipe = new MerchantRecipe(itemStack, 0, 99999, false, 0, 10, true);
      recipe.addIngredient(this.tradeIngredient);
      recipes.add(recipe);
    });

    merchant.setRecipes(recipes);
    playerSender.openMerchant(merchant, true);

    return true;
  }
}