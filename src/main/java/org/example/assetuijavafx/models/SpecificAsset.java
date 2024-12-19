package org.example.assetuijavafx.models;

import java.sql.Date;
import java.util.HashMap;
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

    public AssetPlus getAssetPlus() {
        return assetPlus;
    }

    public void setAssetPlus(AssetPlus assetPlus) {
        this.assetPlus = assetPlus;
    }

    public SpecificAsset() {}

    public SpecificAsset(int assetNumber, Date purchaseDate, int roomNumber, int floorNumber) {
        this.assetNumber = assetNumber;
        this.purchaseDate = purchaseDate;
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
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

    public void setAssetNumber(int assetNumber) {
        this.assetNumber = assetNumber;
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

    public static void delete(SpecificAsset specificAsset) {
        specificassetsByAssetNumber.remove(specificAsset.getAssetNumber());
    }

    public static void addSpecificAsset(SpecificAsset specificAsset) {
        specificassetsByAssetNumber.put(specificAsset.getAssetNumber(), specificAsset);
    }
}
