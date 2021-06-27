package com.txt.craftingInterface;

import java.util.ArrayList;
import java.util.HashMap;

import com.txt.nobsheadsplugin.HeadFactory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftingInterface {
  private JavaPlugin plugin;
  private RecipeKeys recipes;

  @SuppressWarnings("unchecked")
  public CraftingInterface(JavaPlugin plugin) {
    this.plugin = plugin;

    this.recipes = new RecipeKeys();
    RecipeDiscoverer recipeDiscoverer = new RecipeDiscoverer(this.recipes);
    this.plugin.getServer().getPluginManager().registerEvents(recipeDiscoverer, this.plugin);

    ArrayList<HashMap<String, ?>> shaplessRecipes = (ArrayList<HashMap<String, ?>>)plugin.getConfig().getList("recipes.shapeless");
    shaplessRecipes.forEach((shapelessRecipe) -> {
      addShapeless((String)shapelessRecipe.get("result"), (ArrayList<String>)shapelessRecipe.get("input"));
    });

    ArrayList<HashMap<String, ?>> shapedRecipes = (ArrayList<HashMap<String, ?>>)plugin.getConfig().getList("recipes.shaped");
    shapedRecipes.forEach((shapedRecipe) -> {
      HashMap<String, String> rawMaterials = (HashMap<String, String>)shapedRecipe.get("materials");
      HashMap<Character, String> materials = new HashMap<>();
      
      rawMaterials.forEach((key, materialName) -> {
        materials.put(key.toCharArray()[0], materialName);
      });

      addShaped((String)shapedRecipe.get("result"), (ArrayList<String>)shapedRecipe.get("shape"), materials);
    });
  }

  private void addShapeless(String headOwner, ArrayList<String> materialNames) {
    ItemStack itemStack = HeadFactory.getHeadsByName(headOwner, 1);
    ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, headOwner), itemStack);
    
    materialNames.forEach((materialName) -> {
      recipe.addIngredient(Material.getMaterial(materialName));
    });

    this.recipes.addRecipe(recipe.getKey());
    Bukkit.addRecipe(recipe);
  }

  private void addShaped(String headOwner, ArrayList<String> shape, HashMap<Character, String> materialNames) {
    ItemStack itemStack = HeadFactory.getHeadsByName(headOwner, 1);
    ShapedRecipe recipe =  new ShapedRecipe(new NamespacedKey(plugin, headOwner), itemStack);

    recipe.shape(shape.toArray(new String[0]));
    materialNames.forEach((key, materialName) -> {
      recipe.setIngredient(key, Material.getMaterial(materialName));
    });

    this.recipes.addRecipe(recipe.getKey());
    Bukkit.addRecipe(recipe);
  }
}