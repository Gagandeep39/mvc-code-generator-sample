package org.example.assetuijavafx.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetType {

    private static Map<String, AssetType> assettypesByName = new HashMap<>(Map.of(
            "Laptop", new AssetType("Laptop", 5, "laptop.jpg"),
            "Desktop", new AssetType("Desktop", 7, "desktop.jpg"),
            "Printer", new AssetType("Printer", 10, "printer.jpg"),
            "Scanner", new AssetType("Scanner", 8, "scanner.jpg"),
            "Projector", new AssetType("Projector", 6, "projector.jpg"),
            "Router", new AssetType("Router", 4, "router.jpg"),
            "Switch", new AssetType("Switch", 10, "switch.jpg"),
            "Monitor", new AssetType("Monitor", 6, "monitor.jpg"),
            "Server", new AssetType("Server", 12, "server.jpg"),
            "UPS", new AssetType("UPS", 5, "ups.jpg")
    ));

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //AssetType Attributes
    private String name;
    private int expectedLifeSpan;
    private String image;
    private List<SpecificAsset> specificAssets;

    public AssetType(String name, int expectedLifeSpan, String image) {
        this.name = name;
        this.expectedLifeSpan = expectedLifeSpan;
        this.image = image;
    }

    public static Map<String, AssetType> getAssettypesByName() {
        return assettypesByName;
    }

    public static void setAssettypesByName(Map<String, AssetType> assettypesByName) {
        AssetType.assettypesByName = assettypesByName;
    }

    public static void addAssetType(AssetType assetType) {
        assettypesByName.put(assetType.getName(), assetType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpectedLifeSpan() {
        return expectedLifeSpan;
    }

    public void setExpectedLifeSpan(int expectedLifeSpan) {
        this.expectedLifeSpan = expectedLifeSpan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<SpecificAsset> getSpecificAssets() {
        return specificAssets;
    }

    public void setSpecificAssets(List<SpecificAsset> specificAssets) {
        this.specificAssets = specificAssets;
    }
}
