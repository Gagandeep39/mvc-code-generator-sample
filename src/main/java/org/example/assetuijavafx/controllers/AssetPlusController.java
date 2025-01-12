package org.example.assetuijavafx.controllers;


import org.example.assetuijavafx.AssetPlusApplication;
import org.example.assetuijavafx.models.*;
import org.example.assetuijavafx.persistence.AssetPlusPersistence;
import org.example.assetuijavafx.models.MaintenanceTicket.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AssetPlusController {

	public AssetPlusController() {
	}

	public static String updateAssetType(String keyName, String name, int expectedLifeSpan, String image) {
		
		if (!(expectedLifeSpan > 0)) {
			return "The expected life span must be greater than 0.";
		}
	
		if (keyName != name && AssetType.hasWithName(name)) {
			return "The assetType name must be unique.";
		}
	
		try {
			AssetType assetType = AssetType.getWithName(keyName);
			assetType.setName(name);
			assetType.setExpectedLifeSpan(expectedLifeSpan);
			assetType.setImage(image);
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}

	public static String removeAssetType(String name) {
		
		try {
			AssetType assetType = AssetType.getWithName(name);
	  		assetType.delete();
		}
		catch (RuntimeException e) {
	  		return "The assetType does not exist.";
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}

	public static TOAssetType getAssetType(String name) {
		
		if (AssetType.hasWithName(name)) {		
			AssetType assetType = AssetType.getWithName(name);
			return convertToTOAssetType(assetType);
		}
	    
		return null;
	}

	public static List<TOAssetType> getAssetTypesOfAssetPlus() {
		
		AssetPlus root = AssetPlusApplication.getAssetPlus();
		
		if (root.hasAssetTypes()) {
			return convertToTOAssetType(root.getAssetTypes());
		}
		   
		return null;
	}

	public static String updateSpecificAsset(int keyAssetNumber, int assetNumber, int floorNumber, int roomNumber, Date purchaseDate) {
		
		if (!(assetNumber >= 1)) {
			return "The asset number must be greater than or equal to 1.";
		}
	
		if (!(floorNumber >= 0)) {
			return "The floor number must be greater than or equal to 0.";
		}
	
		if (!(roomNumber >= -1)) {
			return "The room number must be greater than or equal to -1.";
		}
	
		if (keyAssetNumber != assetNumber && SpecificAsset.hasWithAssetNumber(assetNumber)) {
			return "The specificAsset assetNumber must be unique.";
		}
	
		try {
			SpecificAsset specificAsset = SpecificAsset.getWithAssetNumber(keyAssetNumber);
			specificAsset.setAssetNumber(assetNumber);
			specificAsset.setFloorNumber(floorNumber);
			specificAsset.setRoomNumber(roomNumber);
			specificAsset.setPurchaseDate(purchaseDate);
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}

	public static String removeSpecificAsset(int assetNumber) {
		
		try {
			SpecificAsset specificAsset = SpecificAsset.getWithAssetNumber(assetNumber);
	  		specificAsset.delete();
		}
		catch (RuntimeException e) {
	  		return "The specificAsset does not exist.";
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}

	public static TOSpecificAsset getSpecificAsset(int assetNumber) {
		
		if (SpecificAsset.hasWithAssetNumber(assetNumber)) {		
			SpecificAsset specificAsset = SpecificAsset.getWithAssetNumber(assetNumber);
			return convertToTOSpecificAsset(specificAsset);
		}
	    
		return null;
	}

	public static List<TOSpecificAsset> getSpecificAssetsOfAssetPlus() {
		
		AssetPlus root = AssetPlusApplication.getAssetPlus();
		
		if (root.hasSpecificAssets()) {
			return convertToTOSpecificAsset(root.getSpecificAssets());
		}
		   
		return null;
	}

	public static String updateMaintenanceTicket(int keyId, int id, Date raisedOnDate, String description, String timeToResolve, String priority) {
		
		TimeEstimate parsedTimeToResolve;
		try {
			parsedTimeToResolve = TimeEstimate.valueOf(timeToResolve);
		}
		catch (Exception e) {		
			return "The timeToResolve must be lessThanADay, oneToThreeDays, threeToSevenDays, oneToThreeWeeks or threeOrMoreWeeks.";
		}
	
		MaintenanceTicket.PriorityLevel parsedPriority;
		try {
			parsedPriority = PriorityLevel.valueOf(priority);
		}
		catch (Exception e) {		
			return "The priority must be Urgent, Normal or Low.";
		}
	
		if (keyId != id && MaintenanceTicket.hasWithId(id)) {
			return "The maintenanceTicket id must be unique.";
		}
	
		try {
			MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(keyId);
			maintenanceTicket.setId(id);
			maintenanceTicket.setRaisedOnDate(raisedOnDate);
			maintenanceTicket.setDescription(description);
			maintenanceTicket.setTimeToResolve(parsedTimeToResolve);
			maintenanceTicket.setPriority(parsedPriority);
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}

	public static String removeMaintenanceTicket(int id) {
		
		try {
			MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(id);
	  		maintenanceTicket.delete();
		}
		catch (RuntimeException e) {
	  		return "The maintenanceTicket does not exist.";
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}

	public static TOMaintenanceTicket getMaintenanceTicket(int id) {
		
		if (MaintenanceTicket.hasWithId(id)) {		
			MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(id);
			return convertToTOMaintenanceTicket(maintenanceTicket);
		}
	    
		return null;
	}

	public static List<TOMaintenanceTicket> getMaintenanceTicketsOfAssetPlus() {
		
		AssetPlus root = AssetPlusApplication.getAssetPlus();
		
		if (root.hasMaintenanceTickets()) {
			return convertToTOMaintenanceTicket(root.getMaintenanceTickets());
		}
		   
		return null;
	}

	private static TOMaintenanceTicket convertToTOMaintenanceTicket(MaintenanceTicket maintenanceTicket) {
      	if (maintenanceTicket == null) {
			return null;
		}
		// TODO Enums are manually converted to String here
		// Acceleo generated a TO umple file which had String
		return new TOMaintenanceTicket(maintenanceTicket.getId(), maintenanceTicket.getRaisedOnDate(), maintenanceTicket.getDescription(), maintenanceTicket.getTimeToResolve().toString(), maintenanceTicket.getPriority().toString());
    }

	private static List<TOMaintenanceTicket> convertToTOMaintenanceTicket(List<MaintenanceTicket> maintenanceTicketList) {
    	List<TOMaintenanceTicket> convertedMaintenanceTicketList = new ArrayList<>();
		
		for (MaintenanceTicket maintenanceTicket: maintenanceTicketList) {
        	convertedMaintenanceTicketList.add(convertToTOMaintenanceTicket(maintenanceTicket));
      	}

      	return convertedMaintenanceTicketList;
	}

	private static TOSpecificAsset convertToTOSpecificAsset(SpecificAsset specificAsset) {
      	if (specificAsset == null) {
			return null;
		}
		return new TOSpecificAsset(specificAsset.getAssetNumber(), specificAsset.getFloorNumber(), specificAsset.getRoomNumber(), specificAsset.getPurchaseDate());
    }

	private static List<TOSpecificAsset> convertToTOSpecificAsset(List<SpecificAsset> specificAssetList) {
    	List<TOSpecificAsset> convertedSpecificAssetList = new ArrayList<>();
		
		for (SpecificAsset specificAsset: specificAssetList) {
        	convertedSpecificAssetList.add(convertToTOSpecificAsset(specificAsset));
      	}

      	return convertedSpecificAssetList;
	}

	private static TOAssetType convertToTOAssetType(AssetType assetType) {
      	if (assetType == null) {
			return null;
		}
		return new TOAssetType(assetType.getName(), assetType.getExpectedLifeSpan(), assetType.getImage());
    }

	private static List<TOAssetType> convertToTOAssetType(List<AssetType> assetTypeList) {
    	List<TOAssetType> convertedAssetTypeList = new ArrayList<>();
		
		for (AssetType assetType: assetTypeList) {
        	convertedAssetTypeList.add(convertToTOAssetType(assetType));
      	}

      	return convertedAssetTypeList;
	}


}

