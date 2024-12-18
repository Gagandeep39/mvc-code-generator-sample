package org.example.assetuijavafx.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetType {

    private static Map<String, AssetType> assettypesByName = new HashMap<>();

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //AssetType Attributes
    private AssetPlus assetPlus;
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

    public static void delete(AssetType type) {
        assettypesByName.remove(type.getName());
    }

    public AssetPlus getAssetPlus() {
        return assetPlus;
    }

    public void setAssetPlus(AssetPlus assetPlus) {
        this.assetPlus = assetPlus;
    }
}
