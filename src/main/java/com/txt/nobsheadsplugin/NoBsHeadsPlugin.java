package com.txt.nobsheadsplugin;

import com.txt.commandInterface.CommandInterface;
import com.txt.craftingInterface.CraftingInterface;

import org.bukkit.plugin.java.JavaPlugin;

public class NoBsHeadsPlugin extends JavaPlugin {
  private CommandInterface commandInterface;

  public NoBsHeadsPlugin() {

  }

  @Override
  public void onEnable(){
    this.saveDefaultConfig();

    this.commandInterface = new CommandInterface(this);
    this.getCommand("heads").setExecutor(commandInterface);
    
    new CraftingInterface(this);
  }

  @Override
  public void onDisable(){
    this.commandInterface.savePurchaseHistory();
  }
}
