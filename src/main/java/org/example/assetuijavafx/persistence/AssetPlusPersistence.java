package org.example.assetuijavafx.persistence;

import org.example.assetuijavafx.AssetPlusApplication;
import org.example.assetuijavafx.model.AssetPlus;

import java.lang.reflect.Field;

public class AssetPlusPersistence {

    //The following fields and setFilename method deal with the json file location.
    private static String filename = "ap.data";
    private static final JsonSerializer serializer = new JsonSerializer(AssetPlusApplication.PACKAGE_NAME);

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

        if (ap == null || hasAnyNullAttribute(ap)) {
            ap = new AssetPlus();
        } else {
            ap.reinitialize();
            System.out.println(ap.getAssetTypes().size());
        }
        return ap;
    }

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
