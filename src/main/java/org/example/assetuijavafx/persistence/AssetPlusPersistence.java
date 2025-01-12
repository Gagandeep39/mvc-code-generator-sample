package org.example.assetuijavafx.persistence;

import org.example.assetuijavafx.AssetPlusApplication;
import org.example.assetuijavafx.models.AssetPlus;

public class AssetPlusPersistence {

  //The following fields and setFilename method deal with the json file location. 
  private static String filename = "ap.data";
  private static JsonSerializer serializer = new JsonSerializer("org.example.acceleooutputplayground");

  public static void setFilename(String filename) {
    AssetPlusPersistence.filename = filename;
  }

  //This method is utilized in the controller methods in order to save the modified objects to the json file. 
  public static void save() {
    save(AssetPlusApplication.getAssetPlus());
  }

  public static void save(AssetPlus ap) {
    serializer.serialize(ap, filename);
  }

  //This method is utilized to load the AssetPlus data back in upon restarting the application.
  public static AssetPlus load() {
    var ap = (AssetPlus) serializer.deserialize(filename);
    
    if (ap == null) {
      ap = new AssetPlus();
    } else {
      ap.reinitialize();
    }
    return ap;
  }

}
