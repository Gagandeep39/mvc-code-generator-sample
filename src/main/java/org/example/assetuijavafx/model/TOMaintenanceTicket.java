/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package org.example.assetuijavafx.model;
import java.sql.Date;

// line 4 "../../../../../../model.ump"
// line 33 "../../../../../../model.ump"
public class TOMaintenanceTicket
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOMaintenanceTicket Attributes
  private int id;
  private Date raisedOnDate;
  private String description;
  private String timeToResolve;
  private String priority;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOMaintenanceTicket(int aId, Date aRaisedOnDate, String aDescription, String aTimeToResolve, String aPriority)
  {
    id = aId;
    raisedOnDate = aRaisedOnDate;
    description = aDescription;
    timeToResolve = aTimeToResolve;
    priority = aPriority;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getId()
  {
    return id;
  }

  public Date getRaisedOnDate()
  {
    return raisedOnDate;
  }

  public String getDescription()
  {
    return description;
  }

  public String getTimeToResolve()
  {
    return timeToResolve;
  }

  public String getPriority()
  {
    return priority;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "," +
            "timeToResolve" + ":" + getTimeToResolve()+ "," +
            "priority" + ":" + getPriority()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "raisedOnDate" + "=" + (getRaisedOnDate() != null ? !getRaisedOnDate().equals(this)  ? getRaisedOnDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}