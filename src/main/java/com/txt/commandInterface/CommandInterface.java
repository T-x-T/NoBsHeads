package com.txt.commandInterface;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.chat.hover.content.Item;

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
    playerSender.getLocation().getWorld().dropItem(playerSender.getLocation(), itemStack);

    return false;
  }
}
