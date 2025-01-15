package org.example.assetuijavafx.models;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MaintenanceTicket
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TimeEstimate { LessThanADay, OneToThreeDays, ThreeToSevenDays, OneToThreeWeeks, ThreeOrMoreWeeks }
  public enum PriorityLevel { Urgent, Normal, Low }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, MaintenanceTicket> maintenanceticketsById = new HashMap<Integer, MaintenanceTicket>();

  //MaintenanceTicket Attributes
  private int id;
  private Date raisedOnDate;
  private String description;
  private TimeEstimate timeToResolve;
  private PriorityLevel priority;

  //MaintenanceTicket State Machines
  public enum Status { Open, Assigned, InProgress, Resolved, Closed }
  private Status status;

  //MaintenanceTicket Associations
  private AssetPlus assetPlus;



  public MaintenanceTicket(int aId, Date aRaisedOnDate, String aDescription, TimeEstimate aTimeToResolve, PriorityLevel aPriority, AssetPlus aAssetPlus)
  {
    raisedOnDate = aRaisedOnDate;
    description = aDescription;
    timeToResolve = aTimeToResolve;
    priority = aPriority;

    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create maintenanceTicket due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public int getId() {
    return id;
  }

  public static Map<Integer, MaintenanceTicket> getMaintenanceticketsById() {
    return maintenanceticketsById;
  }

  public static void setMaintenanceticketsById(Map<Integer, MaintenanceTicket> maintenanceticketsById) {
    MaintenanceTicket.maintenanceticketsById = maintenanceticketsById;
  }

  public Date getRaisedOnDate() {
    return raisedOnDate;
  }

  public void setRaisedOnDate(Date raisedOnDate) {
    this.raisedOnDate = raisedOnDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TimeEstimate getTimeToResolve() {
    return timeToResolve;
  }

  public void setTimeToResolve(TimeEstimate timeToResolve) {
    this.timeToResolve = timeToResolve;
  }

  public PriorityLevel getPriority() {
    return priority;
  }

  public void setPriority(PriorityLevel priority) {
    this.priority = priority;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public AssetPlus getAssetPlus() {
    return assetPlus;
  }

  public static void addMaintenanceTicket(MaintenanceTicket maintenanceTicket) {
    maintenanceticketsById.put(maintenanceTicket.getId(), maintenanceTicket);
  }

  public void delete() {
    maintenanceticketsById.remove(getId());
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
  }

  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public static MaintenanceTicket getWithId(int aId)
  {
    return maintenanceticketsById.get(aId);
  }


  public static  void reinitializeUniqueId(List<MaintenanceTicket> tickets){
    maintenanceticketsById = new HashMap<Integer, MaintenanceTicket>();
    for (MaintenanceTicket t : tickets) {
      maintenanceticketsById.put(t.getId(), t);
    }
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
      existingAssetPlus.removeMaintenanceTicket(this);
    }
    assetPlus.addMaintenanceTicket(this);
    wasSet = true;
    return wasSet;
  }


  public boolean setId(int aId)
  {
    boolean wasSet = false;
    Integer anOldId = getId();
    if (anOldId != null && anOldId.equals(aId)) {
      return true;
    }
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      maintenanceticketsById.remove(anOldId);
    }
    maintenanceticketsById.put(aId, this);
    return wasSet;
  }

}