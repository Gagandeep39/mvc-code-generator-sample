package org.example.assetuijavafx.persistence;

import org.example.assetuijavafx.application.AssetPlusApplication;
import org.example.assetuijavafx.model.AssetPlus;

import java.lang.reflect.Field;

public class AssetPlusPersistence {

    //The following fields and setFilename method deal with the json file location.
    private static String filename = "ap.data";
    private static final JsonSerializer serializer = new JsonSerializer("org.example.assetuijavafx");

    public static void setFilename(String filename) {
        AssetPlusPersistence.filename = filename;
    }

    //This method is utilized in the controller methods in order to save the modified objects to the json file.
    public static void save() {
        save(AssetPlusApplication.getAssetPlus());
    }

    public static void save(AssetPlus root) {
        serializer.serialize(root, filename);
    }

    //This method is utilized to load the AssetPlus data back in upon restarting the application.
    public static AssetPlus load() {
        var root = (AssetPlus) serializer.deserialize(filename);

        if (root == null || hasAnyNullAttribute(root)) {
            root = new AssetPlus();
        } else {
            root.reinitialize();
        }
        return root;
    }

	// Checks if any attribute of root is null
    private static boolean hasAnyNullAttribute(Object obj) {
        if (obj == null) {
            return true;
        }
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true); // Allow access to private fields
                if (field.get(obj) == null) {
                    return true; // At least one attribute is null
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing fields of object", e);
        }
        return false; // No null attributes found
    }
}
