/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package org.example.assetuijavafx.model;

// line 22 "../../../../../../model.ump"
// line 45 "../../../../../../model.ump"
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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAssetType(String aName, int aExpectedLifeSpan, String aImage, boolean aVisible)
  {
    name = aName;
    expectedLifeSpan = aExpectedLifeSpan;
    image = aImage;
    visible = aVisible;
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