package org.example.assetuijavafx.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssetPlus {

    private List<SpecificAsset> specificAssets;
    private List<AssetType> assetTypes;
    private List<MaintenanceTicket> maintenanceTickets;

    public AssetPlus() {
        this.specificAssets = new ArrayList<>();
        this.assetTypes = new ArrayList<>();
        this.maintenanceTickets = new ArrayList<>();
    }


    public List<SpecificAsset> getSpecificAssets() {
        List<SpecificAsset> newSpecificAssets = Collections.unmodifiableList(specificAssets);
        return newSpecificAssets;
    }

    public List<AssetType> getAssetTypes() {
        List<AssetType> newAssetTypes = Collections.unmodifiableList(assetTypes);
        return newAssetTypes;
    }

    public void reinitialize() {
        SpecificAsset.reinitializeUniqueAssetNumber(this.getSpecificAssets());
        AssetType.reinitializeUniqueName(this.getAssetTypes());
        MaintenanceTicket.reinitializeUniqueId(this.getMaintenanceTickets());
    }


    public boolean removeAssetType(AssetType aAssetType)
    {
        boolean wasRemoved = false;
        //Unable to remove aAssetType, as it must always have a assetPlus
        if (!this.equals(aAssetType.getAssetPlus()))
        {
            assetTypes.remove(aAssetType);
            wasRemoved = true;
        }
        return wasRemoved;
    }


    public boolean addAssetType(AssetType aAssetType)
    {
        boolean wasAdded = false;
        if (assetTypes.contains(aAssetType)) { return false; }
        AssetPlus existingAssetPlus = aAssetType.getAssetPlus();
        boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
        if (isNewAssetPlus)
        {
            aAssetType.setAssetPlus(this);
        }
        else
        {
            assetTypes.add(aAssetType);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean hasAssetTypes()
    {
        boolean has = assetTypes.size() > 0;
        return has;
    }

    public boolean addSpecificAsset(SpecificAsset aSpecificAsset)
    {
        boolean wasAdded = false;
        if (specificAssets.contains(aSpecificAsset)) { return false; }
        AssetPlus existingAssetPlus = aSpecificAsset.getAssetPlus();
        boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
        if (isNewAssetPlus)
        {
            aSpecificAsset.setAssetPlus(this);
        }
        else
        {
            specificAssets.add(aSpecificAsset);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeSpecificAsset(SpecificAsset aSpecificAsset)
    {
        boolean wasRemoved = false;
        //Unable to remove aSpecificAsset, as it must always have a assetPlus
        if (!this.equals(aSpecificAsset.getAssetPlus()))
        {
            specificAssets.remove(aSpecificAsset);
            wasRemoved = true;
        }
        return wasRemoved;
    }


    public boolean hasSpecificAssets()
    {
        boolean has = specificAssets.size() > 0;
        return has;
    }


    public boolean hasMaintenanceTickets()
    {
        boolean has = maintenanceTickets.size() > 0;
        return has;
    }

public List<MaintenanceTicket> getMaintenanceTickets()
{
    List<MaintenanceTicket> newMaintenanceTickets = Collections.unmodifiableList(maintenanceTickets);
    return newMaintenanceTickets;
}

}