package com.txt.craftingInterface;

import java.util.ArrayList;

import org.bukkit.NamespacedKey;

class RecipeKeys {
  private ArrayList<NamespacedKey> recipes;

  RecipeKeys() {
    this.recipes = new ArrayList<>();
  }

  void addRecipe(NamespacedKey recipe) {
    this.recipes.add(recipe);
  }

  ArrayList<NamespacedKey> getRecipes() {
    return recipes;
  }
}
