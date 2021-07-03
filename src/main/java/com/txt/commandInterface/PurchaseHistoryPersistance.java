package com.txt.commandInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.txt.nobsheadsplugin.HeadFactory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PurchaseHistoryPersistance {

  @SuppressWarnings("unchecked")
  static HashMap<UUID, ArrayList<String>> loadPurchaseHistory(JavaPlugin plugin) {
    File purchaseHistoryFile = new File("./plugins/nobsheads/purchaseHistory.dat");
    HashMap<UUID, ArrayList<String>> purchaseHistory = new HashMap<>();

    if (!purchaseHistoryFile.exists()) return purchaseHistory;

    try {
      FileInputStream is = new FileInputStream(purchaseHistoryFile);
      ObjectInputStream ois = new ObjectInputStream(is);
      HashMap<UUID, ArrayList<String>> tempPurchaseList = (HashMap<UUID, ArrayList<String>>) ois.readObject();
      ois.close();
      is.close();

      tempPurchaseList.forEach((uuid, names) -> {
        names.removeIf((name) -> {
          return !HeadFactory.isHeadPurchasable(plugin, name);
        });
        purchaseHistory.put(uuid, names);
      });

    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return purchaseHistory;
  }

  public static void savePurchaseHistoryAsyncIfPossible(JavaPlugin plugin, HashMap<UUID, ArrayList<String>> purchaseHistory) {
    if (plugin.getServer().getPluginManager().isPluginEnabled(plugin)) {
      Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
        savePurchaseHistory(purchaseHistory);
      });
    } else {
      savePurchaseHistory(purchaseHistory);
    }
  }

  private static void savePurchaseHistory(HashMap<UUID, ArrayList<String>> purchaseHistory) {
    try {
      FileOutputStream os = new FileOutputStream("./plugins/nobsheads/purchaseHistory.dat");
      ObjectOutputStream oos = new ObjectOutputStream(os);
      oos.writeObject(purchaseHistory);
      oos.close();
      os.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
