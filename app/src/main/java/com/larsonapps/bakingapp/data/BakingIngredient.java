package com.larsonapps.bakingapp.data;

/**
 * Class to hold a baking ingredient
 */
public class BakingIngredient {
    // Declare Variables
    private double mQuantity;
    private String mMeasure;
    private String mIngredient;

    /**
     * Constructor with all fields
     * @param quantity to set
     * @param measure to set
     * @param ingredient to set
     */
    public BakingIngredient(double quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    /**
     * Default constructor
     */
    public BakingIngredient() {}

    /**
     * Getter for quantity
     * @return quantity
     */
    public double getQuantity() {
        return mQuantity;
    }

    /**
     *Setter for quantity
     * @param quantity to set
     */
    public void setQuantity(double quantity) {
        this.mQuantity = quantity;
    }

    /**
     * Getter for measure
     * @return measure
     */
    public String getMeasure() {
        return mMeasure;
    }

    /**
     * Setter for measure
     * @param measure to set
     */
    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    /**
     * Getter for ingredient name
     * @return ingredient name
     */
    public String getIngredient() {
        return mIngredient;
    }

    /**
     * Setter for ingredient name
     * @param ingredient name to set
     */
    public void setIngredient(String ingredient) {
        this.mIngredient = ingredient;
    }
}
