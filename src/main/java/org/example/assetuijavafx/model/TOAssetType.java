/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package org.example.assetuijavafx.model;
import java.util.*;

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

  //TOAssetType Associations
  private List<TOSpecificAsset> specificAssets;

  //Helper Variables
  private boolean canSetSpecificAssets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAssetType(String aName, int aExpectedLifeSpan, String aImage, boolean aVisible)
  {
    name = aName;
    expectedLifeSpan = aExpectedLifeSpan;
    image = aImage;
    visible = aVisible;
    canSetSpecificAssets = true;
    specificAssets = new ArrayList<TOSpecificAsset>();
//    System.out.println(allSpecificAssets.size());
//    boolean didAddSpecificAssets = setSpecificAssets(allSpecificAssets.toArray(new TOSpecificAsset[0]));
//    if (!didAddSpecificAssets)
//    {
//      throw new RuntimeException("Unable to create TOAssetType, must not have duplicate specificAssets. See https://manual.umple.org?RE001ViolationofImmutability.html");
//    }
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
  /* Code from template attribute_IsBoolean */
  public boolean isVisible()
  {
    return visible;
  }
  /* Code from template association_GetMany */
  public TOSpecificAsset getSpecificAsset(int index)
  {
    TOSpecificAsset aSpecificAsset = specificAssets.get(index);
    return aSpecificAsset;
  }

  public List<TOSpecificAsset> getSpecificAssets()
  {
    List<TOSpecificAsset> newSpecificAssets = Collections.unmodifiableList(specificAssets);
    return newSpecificAssets;
  }

  public int numberOfSpecificAssets()
  {
    int number = specificAssets.size();
    return number;
  }

  public boolean hasSpecificAssets()
  {
    boolean has = specificAssets.size() > 0;
    return has;
  }

  public int indexOfSpecificAsset(TOSpecificAsset aSpecificAsset)
  {
    int index = specificAssets.indexOf(aSpecificAsset);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSpecificAssets()
  {
    return 0;
  }
  /* Code from template association_SetUnidirectionalMany */
  private boolean setSpecificAssets(TOSpecificAsset... newSpecificAssets)
  {
    boolean wasSet = false;
    if (!canSetSpecificAssets) { return false; }
    canSetSpecificAssets = false;
    ArrayList<TOSpecificAsset> verifiedSpecificAssets = new ArrayList<TOSpecificAsset>();
    for (TOSpecificAsset aSpecificAsset : newSpecificAssets)
    {
      if (verifiedSpecificAssets.contains(aSpecificAsset))
      {
        continue;
      }
      verifiedSpecificAssets.add(aSpecificAsset);
    }

    if (verifiedSpecificAssets.size() != newSpecificAssets.length)
    {
      return wasSet;
    }

    specificAssets.clear();
    specificAssets.addAll(verifiedSpecificAssets);
    System.out.println("Inside TOAssetType");
    System.out.println(specificAssets);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "expectedLifeSpan" + ":" + getExpectedLifeSpan()+ "," +
            "image" + ":" + getImage()+ "," +
            "visible" + ":" + getVisible()+ "]";
  }
}