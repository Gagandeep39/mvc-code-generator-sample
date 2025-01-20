/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package org.example.assetuijavafx.model;

// line 21 "../../../../../../model.ump"
// line 43 "../../../../../../model.ump"
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

  public boolean getVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
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

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "expectedLifeSpan" + ":" + getExpectedLifeSpan()+ "," +
            "image" + ":" + getImage()+ "]";
  }
}