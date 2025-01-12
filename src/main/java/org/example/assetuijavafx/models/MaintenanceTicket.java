package org.example.assetuijavafx.models;

import java.sql.Date;
import java.util.HashMap;
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public void setAssetPlus(AssetPlus assetPlus) {
    this.assetPlus = assetPlus;
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
}