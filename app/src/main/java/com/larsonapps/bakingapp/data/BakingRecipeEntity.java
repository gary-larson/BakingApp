package com.larsonapps.bakingapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class to define the baking_recipe table for room
 */
@Entity(tableName = "backing_recipe")
public class BakingRecipeEntity {
    // Declare Variables
    @PrimaryKey
    @ColumnInfo(name = "recipe_id")
    private int mId;

    @ColumnInfo(name = "recipe_name")
    private String mName;

    @ColumnInfo(name = "servings")
    private int mServings;

    @ColumnInfo(name = "image")
    private String mImage;

    /**
     * Constructor for all variables
     * @param id to set
     * @param name to set
     * @param servings to set
     * @param image to set
     */
    public BakingRecipeEntity (int id, String name, int servings, String image) {
        mId = id;
        mName = name;
        mServings = servings;
        mImage = image;
    }

    /**
     * Getter for recipe id
     * @return id
     */
    public int getId() {
        return mId;
    }

    /**
     * Setter for recipe id
     * @param id to set
     */
    public void setId(int id) {
        this.mId = id;
    }

    /**
     * Getter for recipename
     * @return recipe name
     */
    public String getName() {
        return mName;
    }

    /**
     * Setter for recipe name
     * @param name to set
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Getter for recipe servings
     * @return recipe servings
     */
    public int getServings() {
        return mServings;
    }

    /**
     * Setter for recipr servings
     * @param servings to set
     */
    public void setServings(int servings) {
        this.mServings = servings;
    }

    /**
     * Getter for recipe image
     * @return recipe image
     */
    public String getImage() {
        return mImage;
    }

    /**
     * Setter for recipe image
     * @param image to set
     */
    public void setImage(String image) {
        this.mImage = image;
    }
}

