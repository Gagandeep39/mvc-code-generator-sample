package org.example.assetuijavafx.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetType {

    private static Map<String, AssetType> assettypesByName = new HashMap<String, AssetType>();

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //AssetType Attributes
    private String name;
    private int expectedLifeSpan;
    private String image;
    private List<SpecificAsset> specificAssets;
}
