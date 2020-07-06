package com.larsonapps.bakingapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Class to hold a baking ingredient and define the ingredients table for room
 */
@Entity (tableName = "ingredients", indices = {@Index("recipe_key")},
        foreignKeys = @ForeignKey(entity = BakingRecipeEntity.class,
        parentColumns = "recipe_id",
        childColumns = "recipe_key",
        onDelete = ForeignKey.NO_ACTION))
public class BakingIngredient {
    // Declare Variables
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ingredient_key")
    private int mIngredientKey;

    @ColumnInfo(name = "recipe_key")
    private int mRecipeKey;

    @ColumnInfo(name = "quantity")
    private double mQuantity;

    @ColumnInfo(name = "measure")
    private String mMeasure;

    @ColumnInfo(name = "ingredient")
    private String mIngredient;

    /**
     * Constructor with all fields
     * @param ingredientKey to set
     * @param recipeKey to set
     * @param quantity to set
     * @param measure to set
     * @param ingredient to set
     */
    public BakingIngredient(int ingredientKey, int recipeKey, double quantity, String measure,
                            String ingredient) {
        mIngredientKey = ingredientKey;
        mRecipeKey = recipeKey;
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    /**
     * Default constructor
     */
    @Ignore
    public BakingIngredient() {}

    /**
     * Getter for ingredient key
     * @return ingredient key
     */
    public int getIngredientKey() {
        return mIngredientKey;
    }

    /**
     * Setter for ingredient key
     * @param ingredientKey to set
     */
    public void setIngredientKey(int ingredientKey) {
        this.mIngredientKey = ingredientKey;
    }

    /**
     * Getter for recipe key
     * @return recipe key
     */
    public int getRecipeKey() {
        return mRecipeKey;
    }

    /**
     * Setter for recipe key
     * @param recipeKey to set
     */
    public void setRecipeKey(int recipeKey) {
        this.mRecipeKey = recipeKey;
    }

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
