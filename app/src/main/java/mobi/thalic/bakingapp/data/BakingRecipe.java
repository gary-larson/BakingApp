package mobi.thalic.bakingapp.data;

import java.util.List;

/**
 * Class to deal with a baking recipe
 */
public class BakingRecipe {
    // Declare Variables
    private int mId;
    private String mName;
    private List<BakingIngredient> mIngredientList;
    private List<BakingStep> mStepList;
    private int mServings;
    private String mImage;

    /**
     * Constructor for all variables
     * @param id to set
     * @param name to set
     * @param ingredientList to set
     * @param stepList to set
     * @param servings to set
     * @param image to set
     */
    public BakingRecipe (int id, String name, List<BakingIngredient> ingredientList,
                         List<BakingStep> stepList, int servings, String image) {
        mId = id;
        mName = name;
        mIngredientList = ingredientList;
        mStepList = stepList;
        mServings = servings;
        mImage = image;
    }

    /**
     * Default constructor
     */
    public BakingRecipe(){}

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
     * Getter for recipe ingredient list
     * @return recipe ingredient list
     */
    public List<BakingIngredient> getIngredientList() {
        return mIngredientList;
    }

    /**
     * Setter for recipe ingredient list
     * @param ingredientList to set
     */
    public void setIngredientList(List<BakingIngredient> ingredientList) {
        this.mIngredientList = ingredientList;
    }

    /**
     * Getter for recipe step list
     * @return recipe step list
     */
    public List<BakingStep> getStepList() {
        return mStepList;
    }

    /**
     * Setter for recipe step list
     * @param stepList to set
     */
    public void setStepList(List<BakingStep> stepList) {
        this.mStepList = stepList;
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
