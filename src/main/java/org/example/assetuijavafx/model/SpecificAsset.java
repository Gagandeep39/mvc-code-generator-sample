package org.example.assetuijavafx.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecificAsset {


    private static Map<Integer, SpecificAsset> specificassetsByAssetNumber = new HashMap<Integer, SpecificAsset>();

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //SpecificAsset Attributes
    private int assetNumber;
    private int floorNumber;
    private int roomNumber;
    private Date purchaseDate;
    private AssetPlus assetPlus;
    private AssetType assetType;

    public AssetPlus getAssetPlus() {
        return assetPlus;
    }


    public SpecificAsset() {}

    public SpecificAsset(int aAssetNumber, int aFloorNumber, int aRoomNumber, Date aPurchaseDate, AssetPlus aAssetPlus, AssetType aAssetType)
    {
        floorNumber = aFloorNumber;
        roomNumber = aRoomNumber;
        purchaseDate = aPurchaseDate;
        if (!setAssetNumber(aAssetNumber))
        {
            throw new RuntimeException("Cannot create due to duplicate assetNumber. See http://manual.umple.org?RE003ViolationofUniqueness.html");
        }
        boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
        if (!didAddAssetPlus)
        {
            throw new RuntimeException("Unable to create specificAsset due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        boolean didAddAssetType = setAssetType(aAssetType);
        if (!didAddAssetType)
        {
            throw new RuntimeException("Unable to create specificAsset due to assetType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    public static Map<Integer, SpecificAsset> getSpecificassetsByAssetNumber() {
        return specificassetsByAssetNumber;
    }

    public static void setSpecificassetsByAssetNumber(Map<Integer, SpecificAsset> specificassetsByAssetNumber) {
        SpecificAsset.specificassetsByAssetNumber = specificassetsByAssetNumber;
    }

    public int getAssetNumber() {
        return assetNumber;
    }

    public boolean setAssetNumber(int aAssetNumber)
    {
        boolean wasSet = false;
        Integer anOldAssetNumber = getAssetNumber();
        if (anOldAssetNumber != null && anOldAssetNumber.equals(aAssetNumber)) {
            return true;
        }
        if (hasWithAssetNumber(aAssetNumber)) {
            return wasSet;
        }
        assetNumber = aAssetNumber;
        wasSet = true;
        if (anOldAssetNumber != null) {
            specificassetsByAssetNumber.remove(anOldAssetNumber);
        }
        specificassetsByAssetNumber.put(aAssetNumber, this);
        return wasSet;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void delete()
    {
        specificassetsByAssetNumber.remove(getAssetNumber());
        AssetPlus placeholderAssetPlus = assetPlus;
        this.assetPlus = null;
        if(placeholderAssetPlus != null)
        {
            placeholderAssetPlus.removeSpecificAsset(this);
        }
    }

    public static void addSpecificAsset(SpecificAsset specificAsset) {
        specificassetsByAssetNumber.put(specificAsset.getAssetNumber(), specificAsset);
    }


    public static  void reinitializeUniqueAssetNumber(List<SpecificAsset> assets){
        specificassetsByAssetNumber = new HashMap<Integer, SpecificAsset>();
        for (SpecificAsset a : assets) {
            specificassetsByAssetNumber.put(a.getAssetNumber(), a);
        }
    }


    public static boolean hasWithAssetNumber(int aAssetNumber)
    {
        return getWithAssetNumber(aAssetNumber) != null;
    }

    public static SpecificAsset getWithAssetNumber(int aAssetNumber)
    {
        return specificassetsByAssetNumber.get(aAssetNumber);
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
            existingAssetPlus.removeSpecificAsset(this);
        }
        assetPlus.addSpecificAsset(this);
        wasSet = true;
        return wasSet;
    }

    public AssetType getAssetType()
    {
        return assetType;
    }

    /* Code from template association_SetOneToMany */
    public boolean setAssetType(AssetType aAssetType)
    {
        boolean wasSet = false;
        if (aAssetType == null)
        {
            return wasSet;
        }

        AssetType existingAssetType = assetType;
        assetType = aAssetType;
        if (existingAssetType != null && !existingAssetType.equals(aAssetType))
        {
            existingAssetType.removeSpecificAsset(this);
        }
        assetType.addSpecificAsset(this);
        wasSet = true;
        return wasSet;
    }

}
