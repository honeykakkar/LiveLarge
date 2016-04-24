package com.su.honey.livelarge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by honey on 4/22/2016.
 */

@JsonIgnoreProperties({"selection"})
public class SearchParams implements Serializable
{
    public String getPropertyType() {
        return PropertyType;
    }

    public void setPropertyType(String propertyType) {
        PropertyType = propertyType;
    }

    public String getFurnished() {
        return Furnished;
    }

    public void setFurnished(String furnished) {
        Furnished = furnished;
    }

    public int getBedrooms() {
        return Bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        Bedrooms = bedrooms;
    }

    public int getMinArea() {
        return MinArea;
    }

    public void setMinArea(int minArea) {
        MinArea = minArea;
    }

    public int getMaxArea() {
        return MaxArea;
    }

    public void setMaxArea(int maxArea) {
        MaxArea = maxArea;
    }

    public int getMinBudget() {
        return MinBudget;
    }

    public void setMinBudget(int minBudget) {
        MinBudget = minBudget;
    }

    public int getMaxBudget() {
        return MaxBudget;
    }

    public void setMaxBudget(int maxBudget) {
        MaxBudget = maxBudget;
    }

    public Boolean getRent() {
        return Rent;
    }

    public void setRent(Boolean rent) {
        Rent = rent;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public SearchParams(String propertyType, String furnished, int bedrooms, int minArea, int maxArea, int minBudget, int maxBudget, Boolean rent, String locality) {
        PropertyType = propertyType;
        Furnished = furnished;
        Bedrooms = bedrooms;
        MinArea = minArea;
        MaxArea = maxArea;
        MinBudget = minBudget;
        MaxBudget = maxBudget;
        Rent = rent;
        Locality = locality;
    }

    private String PropertyType;
    private String Furnished;
    private int Bedrooms;
    private int MinArea;
    private int MaxArea;
    private int MinBudget;
    private int MaxBudget;
    private Boolean Rent;
    private String Locality;
}
