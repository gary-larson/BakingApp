package com.larsonapps.bakingapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Class to deal with a baking step and define the steps table for room
 */
@Entity(tableName = "steps")
public class BakingStep {
    // Declare Variables
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "step_key")
    private int mStepKey;

    @ColumnInfo(name = "recipe_key")
    private int mRecipeKey;

    @ColumnInfo(name = "step_id")
    private int mId;

    @ColumnInfo(name = "short_description")
    private String mShortDescription;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "video_url")
    private String mVideoURL;

    @ColumnInfo(name = "thumbnail_url")
    private String mThumbnailURL;

    /**
     * Constructor for all variables
     * @param stepKey to set
     * @param recipeKey to set
     * @param id to set
     * @param shortDescription to set
     * @param description to set
     * @param videoURL to set
     * @param thumbnailURL to set
     */
    public BakingStep(int stepKey, int recipeKey, int id, String shortDescription,
                      String description, String videoURL, String thumbnailURL) {
        mStepKey = stepKey;
        mRecipeKey = recipeKey;
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailURL = thumbnailURL;
    }

    /**
     * Default constructor
     */
    @Ignore
    public BakingStep() {}

    /**
     * Getter for step key
     * @return step key
     */
    public int getStepKey() {
        return mStepKey;
    }

    /**
     * Setter for step key
     * @param stepKey to set
     */
    public void setStepKey(int stepKey) {
        this.mStepKey = stepKey;
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
     * Getter for step id
     * @return step id
     */
    public int getId() {
        return mId;
    }

    /**
     * Setter for step id
     * @param id to set
     */
    public void setId(int id) {
        this.mId = id;
    }

    /**
     * Getter for step short description
     * @return step short description
     */
    public String getShortDescription() {
        return mShortDescription;
    }

    /**
     * Setter for step short description
     * @param shortDescription to set
     */
    public void setShortDescription(String shortDescription) {
        this.mShortDescription = shortDescription;
    }

    /**
     * Getter for step description
     * @return step description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Setter for step description
     * @param description to set
     */
    public void setDescription(String description) {
        this.mDescription = description;
    }

    /**
     * Getter for step video url
     * @return step video url
     */
    public String getVideoURL() {
        return mVideoURL;
    }

    /**
     * Setter for step video url
     * @param videoURL to set
     */
    public void setVideoURL(String videoURL) {
        this.mVideoURL = videoURL;
    }

    /**
     * Getter for step thuimbnail url
     * @return step thumbnail url
     */
    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    /**
     * Setter for step thumbnail url
     * @param thumbnailURL to set
     */
    public void setThumbnailURL(String thumbnailURL) {
        this.mThumbnailURL = thumbnailURL;
    }
}
