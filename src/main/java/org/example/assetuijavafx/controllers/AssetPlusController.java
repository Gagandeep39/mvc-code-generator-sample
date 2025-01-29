package org.example.assetuijavafx.controllers;

import org.example.assetuijavafx.application.AssetPlusApplication;
import org.example.assetuijavafx.model.*;
import org.example.assetuijavafx.persistence.AssetPlusPersistence;
import org.example.assetuijavafx.model.AssetPlus.*;
import org.example.assetuijavafx.model.MaintenanceTicket.*;
import org.example.assetuijavafx.model.SpecificAsset.*;
import org.example.assetuijavafx.model.AssetType.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AssetPlusController {

	public AssetPlusController() {
	}

	public static String addAssetType() {
		
		AssetPlus root = AssetPlusApplication.getAssetPlus();
		
		try {
			new AssetType();
		}
		catch (RuntimeException e) {
			return "The assetType name must be unique.";
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}
	

	public static String addAssetType(String name, int expectedLifeSpan, String image, boolean visible) {
		
		AssetPlus root = AssetPlusApplication.getAssetPlus();
		
		if (!(expectedLifeSpan > 0)) {
			return "The expected life span must be greater than 0.";
		}
	
		try {
			new AssetType(name, expectedLifeSpan, image, visible, root);
		}
		catch (RuntimeException e) {
			return "The assetType name must be unique.";
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}
	

	public static String updateAssetType(String keyName, String name, int expectedLifeSpan, String image, boolean visible) {
		
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
			assetType.setVisible(visible);
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

	public static String addSpecificAsset() {
		
		AssetPlus root = AssetPlusApplication.getAssetPlus();
		
		try {
			new SpecificAsset();
		}
		catch (RuntimeException e) {
			return "The specificAsset assetNumber must be unique.";
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}
	

	public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber, Date purchaseDate, String assetTypeName) {
		
		AssetPlus root = AssetPlusApplication.getAssetPlus();
		
		if (!(assetNumber >= 1)) {
			return "The asset number must be greater than or equal to 1.";
		}
	
		if (!(floorNumber >= 0)) {
			return "The floor number must be greater than or equal to 0.";
		}
	
		if (!(roomNumber >= -1)) {
			return "The room number must be greater than or equal to -1.";
		}
	
		AssetType assetType;
		try {
			assetType = AssetType.getWithName(assetTypeName);
		}
		catch (RuntimeException e) {
			return "The assetType does not exist.";
		}
	
		try {
			new SpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, root, assetType);
		}
		catch (RuntimeException e) {
			return "The specificAsset assetNumber must be unique.";
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}
	

	public static String updateSpecificAsset(int keyAssetNumber, int assetNumber, int floorNumber, int roomNumber, Date purchaseDate, String assetTypeName) {
		
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
			AssetType assetType;
			try {
				assetType = AssetType.getWithName(assetTypeName);
			}
			catch (RuntimeException e) {
				return "The assetType does not exist.";
			}
			specificAsset.setAssetType(assetType);
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

	public static String addMaintenanceTicket(int id, Date raisedOnDate, String description, String timeToResolve, String priority) {
		
		AssetPlus root = AssetPlusApplication.getAssetPlus();
		
		TimeEstimate parsedTimeToResolve;
		try {
			parsedTimeToResolve = TimeEstimate.valueOf(timeToResolve);
		}
		catch (Exception e) {		
			return "The timeToResolve must be lessThanADay, oneToThreeDays, threeToSevenDays, oneToThreeWeeks or threeOrMoreWeeks.";
		}
	
		PriorityLevel parsedPriority;
		try {
			parsedPriority = PriorityLevel.valueOf(priority);
		}
		catch (Exception e) {		
			return "The priority must be Urgent, Normal or Low.";
		}
	
		try {
			new MaintenanceTicket(id, raisedOnDate, description, parsedTimeToResolve, parsedPriority, root);
		}
		catch (RuntimeException e) {
			return "The maintenanceTicket id must be unique.";
		}
	
		try {
			AssetPlusPersistence.save();
		}
		catch (RuntimeException e) {
			return e.getMessage();
		}
	
		return "";
	}
	

	public static String updateMaintenanceTicket(int keyId, int id, Date raisedOnDate, String description, String timeToResolve, String priority) {
		
		TimeEstimate parsedTimeToResolve;
		try {
			parsedTimeToResolve = TimeEstimate.valueOf(timeToResolve);
		}
		catch (Exception e) {		
			return "The timeToResolve must be lessThanADay, oneToThreeDays, threeToSevenDays, oneToThreeWeeks or threeOrMoreWeeks.";
		}
	
		PriorityLevel parsedPriority;
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
		return new TOSpecificAsset(specificAsset.getAssetNumber(), specificAsset.getFloorNumber(), specificAsset.getRoomNumber(), specificAsset.getPurchaseDate(), specificAsset.getAssetType().getName());
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
		return new TOAssetType(assetType.getName(), assetType.getExpectedLifeSpan(), assetType.getImage(), assetType.getVisible(), assetType.getSpecificAssets().stream().map(specificAsset -> specificAsset.getAssetNumber()).toList());
    }

	private static List<TOAssetType> convertToTOAssetType(List<AssetType> assetTypeList) {
    	List<TOAssetType> convertedAssetTypeList = new ArrayList<>();
		
		for (AssetType assetType: assetTypeList) {
        	convertedAssetTypeList.add(convertToTOAssetType(assetType));
      	}

      	return convertedAssetTypeList;
	}


}

