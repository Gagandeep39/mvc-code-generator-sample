package org.example.assetuijavafx.models;

import java.util.ArrayList;
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

    public AssetType() {}


    public AssetType(String aName, int aExpectedLifeSpan, String aImage, AssetPlus aAssetPlus)
    {
        expectedLifeSpan = aExpectedLifeSpan;
        image = aImage;
        if (!setName(aName))
        {
            throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
        }
        boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
        if (!didAddAssetPlus)
        {
            throw new RuntimeException("Unable to create assetType due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        specificAssets = new ArrayList<SpecificAsset>();
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

    public boolean setName(String aName)
    {
        boolean wasSet = false;
        String anOldName = getName();
        if (anOldName != null && anOldName.equals(aName)) {
            return true;
        }
        if (hasWithName(aName)) {
            return wasSet;
        }
        name = aName;
        wasSet = true;
        if (anOldName != null) {
            assettypesByName.remove(anOldName);
        }
        assettypesByName.put(aName, this);
        return wasSet;
    }


    public static boolean hasWithName(String aName)
    {
        return getWithName(aName) != null;
    }


    public static AssetType getWithName(String aName)
    {
        return assettypesByName.get(aName);
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

    public void delete()
    {
        assettypesByName.remove(getName());
        AssetPlus placeholderAssetPlus = assetPlus;
        this.assetPlus = null;
        if(placeholderAssetPlus != null)
        {
            placeholderAssetPlus.removeAssetType(this);
        }
    }

    public AssetPlus getAssetPlus() {
        return assetPlus;
    }

    public boolean setAssetPlus(AssetPlus aAssetPlus)
    {
        boolean wasSet = false;
        if (aAssetPlus == null)
        {
            return wasSet;
        }

        AssetPlus existingAssetPlus = assetPlus;
        assetPlus = aAssetPlus;
        if (existingAssetPlus != null && !existingAssetPlus.equals(aAssetPlus))
        {
            existingAssetPlus.removeAssetType(this);
        }
        assetPlus.addAssetType(this);
        wasSet = true;
        return wasSet;
    }


    public static  void reinitializeUniqueName(List<AssetType> types){
        assettypesByName = new HashMap<String, AssetType>();
        for (AssetType t : types) {
            assettypesByName.put(t.getName(), t);
        }
    }
}
