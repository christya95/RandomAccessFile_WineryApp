/*
    Wine.java
    Author: H.D
    Date: n / a

    Description
    A model class that models a wine object
*/

package models;

import java.time.LocalDate;

public class Wine {
    
    private int wineID, year, quantity;
    private String estate, grape;
    private double price;

    /**
     * The default no-argument constructor
     */
    public Wine(){}
    
    /**
     * Constructor for Wine object. It does not use wineID, since it will be
     * calculated when the object is created
     * @param estate the estate where the grape was produced
     * @param grape the grape variety
     * @param year the year it was produced
     * @param quantity the number of bottles
     * @param price the price per bottle
     */
    public Wine(String estate, String grape, int year, int quantity, 
            double price) {
        setEstate(estate);
        setGrape(grape);
        setYear(year);
        setQuantity(quantity);
        setPrice(price);
    }

    /**
     * Accessor for getting the wineID
     * @return the unique wineID for a wine
     */
    public int getWineID() {
        return wineID;
    }

    /**
     * Mutator for setting the wineID
     * @param wineID the unique wineID that identifies a particular wine
     * @throws IllegalArgumentException when ID is negative number
     */
    public void setWineID(int wineID) throws IllegalArgumentException{
        if (wineID >= 0) {
            this.wineID = wineID;
        }else{
            throw new IllegalArgumentException("Wine ID be >=0");
        }
    }

    /**
     * Accessor for the estate property of the wine
     * @return the estate where grape was produced
     */
    public String getEstate() {
        return estate;
    }

    /**
     * Mutator for setting the estate
     * @param estate the estate where grape was produced
     */
    public void setEstate(String estate) {
        this.estate = estate; 
    }

    /**
     * Accessor for getting the grape variety
     * @return the grape variety
     */
    public String getGrape() {
        return grape;
    }

    /**
     * Mutator for setting the grape variety
     * @param grape the grape variety
     */
    public void setGrape(String grape) {
        this.grape = grape;
    }

    /**
     * Accessor for getting the year when the grape was produced
     * @return the year when grape was produced
     */
    public int getYear() {
        return year;
    }

    /**
     * Mutator for setting the year when the grape was produced
     * @param year the year when grape was produced
     * @throws IllegalArgumentException values that are not between 1980 and 
     * the present year are rejected
     */
    public void setYear(int year) throws IllegalArgumentException{
        if (year < 1980 || year > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Unacceptable year");
        } else {
            this.year = year;
        }
    }

    /**
     * Accessor for getting the number of bottles for a particular wine
     * @return the number of bottles
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Mutator for setting the quantity. 
     * @param quantity the number of bottles
     * @throws IllegalArgumentException non-positive values are rejected
     */
    public void setQuantity(int quantity) throws IllegalArgumentException{
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Quantity must be positive number");
        }
        
    }

    /**
     * Accessor for getting the price of the bottle
     * @return the price of one bottle
     */
    public double getPrice() {
        return price;
    }

    /**
     * Mutator for setting the price of the bottle
     * @param price the price of one bottle
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
