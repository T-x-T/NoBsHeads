package com.txt.craftingInterface;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

class RecipeDiscoverer implements Listener {
  private RecipeKeys recipes;

  RecipeDiscoverer(RecipeKeys recipes) {
    this.recipes = recipes;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.getPlayer().discoverRecipes(recipes.getRecipes());
  }
}
