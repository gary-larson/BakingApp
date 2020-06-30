package com.larsonapps.bakingapp.data;

public class BakingStep {
    // Declare Variables
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;

    /**
     * Constructor for all variables
     * @param id to set
     * @param shortDescription to set
     * @param description to set
     * @param videoURL to set
     * @param thumbnailURL to set
     */
    public BakingStep(int id, String shortDescription, String description, String videoURL,
                      String thumbnailURL) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailURL = thumbnailURL;
    }

    /**
     * Default constructor
     */
    public BakingStep() {}

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
