package com.lab.bean;
import java.math.BigDecimal;
public class Chemical {
    private String chemicalID;
    private String chemicalName;
    private String category;
    private String unitOfMeasure;
    private BigDecimal reorderLevel;
    private String hazardLevel;
    private String shelfLocation;
    private String status;
    public Chemical() {

    }
    public Chemical(String chemicalID, String chemicalName, String category,String unitOfMeasure, BigDecimal reorderLevel,String hazardLevel, String shelfLocation, String status) {
        this.chemicalID = chemicalID;
        this.chemicalName = chemicalName;
        this.category = category;
        this.unitOfMeasure = unitOfMeasure;
        this.reorderLevel = reorderLevel;
        this.hazardLevel = hazardLevel;
        this.shelfLocation = shelfLocation;
        this.status = status;
    }
    public String getChemicalID() {
        return chemicalID;
    }

    public void setChemicalID(String chemicalID) {
        this.chemicalID = chemicalID;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public BigDecimal getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(BigDecimal reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getHazardLevel() {
        return hazardLevel;
    }

    public void setHazardLevel(String hazardLevel) {
        this.hazardLevel = hazardLevel;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}