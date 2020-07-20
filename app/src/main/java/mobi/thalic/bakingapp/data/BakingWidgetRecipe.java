package mobi.thalic.bakingapp.data;

import java.util.List;

/**
 * Class to handle with baking widget recipe data
 */
public class BakingWidgetRecipe {
    //Declare variables
    private String mRecipeName;
    private List<String> mBakingIngredientList;

    /**
     * Default Constructor
     */
    public BakingWidgetRecipe() {}

    /**
     * Getter for recipe name
     * @return recipe name
     */
    public String getRecipeName() {
        return mRecipeName;
    }

    /**
     * Setter for recipe name
     * @param recipeName to set
     */
    public void setRecipeName(String recipeName) {
        this.mRecipeName = recipeName;
    }

    /**
     * Getter for baking ingredient list
     * @return baking ingredient list
     */
    public List<String> getBakingIngredientList() {
        return mBakingIngredientList;
    }

    /**
     * Setter for baking ingredient list
     * @param bakingIngredientList to set
     */
    public void setBakingIngredientList(List<String> bakingIngredientList) {
        this.mBakingIngredientList = bakingIngredientList;
    }
}
