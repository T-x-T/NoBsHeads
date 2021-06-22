package com.txt.nobsheadsplugin;

import com.txt.commandInterface.CommandInterface;
import com.txt.craftingInterface.CraftingInterface;

import org.bukkit.plugin.java.JavaPlugin;

public class NoBsHeadsPlugin extends JavaPlugin {
  public NoBsHeadsPlugin() {

  }

  @Override
  public void onEnable(){
    CommandInterface commandInterface = new CommandInterface();
    this.getCommand("heads").setExecutor(commandInterface);
    
    new CraftingInterface(this);
  }

  @Override
  public void onDisable(){

  }
}
