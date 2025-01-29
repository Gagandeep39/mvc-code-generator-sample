/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package org.example.assetuijavafx.model;

import java.util.List;

// line 22 "../../../../../model.ump"
// line 46 "../../../../../model.ump"
public class TOAssetType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAssetType Attributes
  private String name;
  private int expectedLifeSpan;
  private String image;
  private boolean visible;
  private List<Integer> assetNumberForSpecificAssets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAssetType(String aName, int aExpectedLifeSpan, String aImage, boolean aVisible, List<Integer> aAssetNumberForSpecificAssets)
  {
    name = aName;
    expectedLifeSpan = aExpectedLifeSpan;
    image = aImage;
    visible = aVisible;
    assetNumberForSpecificAssets = aAssetNumberForSpecificAssets;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getName()
  {
    return name;
  }

  public int getExpectedLifeSpan()
  {
    return expectedLifeSpan;
  }

  public String getImage()
  {
    return image;
  }

  public boolean getVisible()
  {
    return visible;
  }

  public List<Integer> getAssetNumberForSpecificAssets()
  {
    return assetNumberForSpecificAssets;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isVisible()
  {
    return visible;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "expectedLifeSpan" + ":" + getExpectedLifeSpan()+ "," +
            "image" + ":" + getImage()+ "," +
            "visible" + ":" + getVisible()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "assetNumberForSpecificAssets" + "=" + (getAssetNumberForSpecificAssets() != null ? !getAssetNumberForSpecificAssets().equals(this)  ? getAssetNumberForSpecificAssets().toString().replaceAll("  ","    ") : "this" : "null");
  }
}