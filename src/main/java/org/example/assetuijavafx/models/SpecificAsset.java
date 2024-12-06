package org.example.assetuijavafx.models;

import java.util.Date;
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

    private AssetType assetType;
}
