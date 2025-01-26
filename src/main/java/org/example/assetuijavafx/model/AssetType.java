package org.example.assetuijavafx.model;

import java.util.*;

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
    private boolean visible;
    private List<SpecificAsset> specificAssets;

    public AssetType() {}


    public AssetType(String aName, int aExpectedLifeSpan, String aImage, boolean aVisible,  AssetPlus aAssetPlus)
    {
        expectedLifeSpan = aExpectedLifeSpan;
        image = aImage;
        visible = aVisible;
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

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public boolean removeSpecificAsset(SpecificAsset aSpecificAsset)
    {
        boolean wasRemoved = false;
        //Unable to remove aSpecificAsset, as it must always have a assetType
        if (!this.equals(aSpecificAsset.getAssetType()))
        {
            specificAssets.remove(aSpecificAsset);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public boolean addSpecificAsset(SpecificAsset aSpecificAsset)
    {
        boolean wasAdded = false;
        if (specificAssets.contains(aSpecificAsset)) { return false; }
        AssetType existingAssetType = aSpecificAsset.getAssetType();
        boolean isNewAssetType = existingAssetType != null && !this.equals(existingAssetType);
        if (isNewAssetType)
        {
            aSpecificAsset.setAssetType(this);
        }
        else
        {
            specificAssets.add(aSpecificAsset);
        }
        wasAdded = true;
        return wasAdded;
    }

    /* Code from template association_GetMany */
    public SpecificAsset getSpecificAsset(int index)
    {
        SpecificAsset aSpecificAsset = specificAssets.get(index);
        return aSpecificAsset;
    }

    public List<SpecificAsset> getSpecificAssets()
    {
        List<SpecificAsset> newSpecificAssets = Collections.unmodifiableList(specificAssets);
        return newSpecificAssets;
    }

}
