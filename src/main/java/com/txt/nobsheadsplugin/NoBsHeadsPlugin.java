package com.txt.nobsheadsplugin;

import com.txt.commandInterface.CommandInterface;

import org.bukkit.plugin.java.JavaPlugin;

public class NoBsHeadsPlugin extends JavaPlugin {
  public NoBsHeadsPlugin() {

  }

  @Override
  public void onEnable(){
    CommandInterface commandInterface = new CommandInterface();
    this.getCommand("heads").setExecutor(commandInterface);  
  }

  @Override
  public void onDisable(){

  }
}
