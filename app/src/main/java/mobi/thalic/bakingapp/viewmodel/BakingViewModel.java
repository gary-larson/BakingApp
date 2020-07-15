package mobi.thalic.bakingapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import mobi.thalic.bakingapp.data.BakingIngredient;
import mobi.thalic.bakingapp.data.BakingRecipeEntity;
import mobi.thalic.bakingapp.data.BakingRepository;
import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.utilities.BakingResult;

/**
 * Class to handle baking view model
 */
public class BakingViewModel extends AndroidViewModel {
    // Declare variables
    Application mApplication;
    BakingRepository mBakingRepository;
    private int mRecipeKey;
    private String mRecipeName;
    private BakingStep mBakingStep;
    LiveData<BakingResult<List<BakingRecipeEntity>>> mBakingRecipes;
    LiveData<List<BakingIngredient>> mBakingIngredients;
    LiveData<List<BakingStep>> mBakingSteps;

    /**
     * Constructor foe baking view model
     * @param application to be used in the repository
     */
    public BakingViewModel (Application application) {
        super(application);
        this.mApplication = application;
        mBakingRepository = new BakingRepository(mApplication);
    }

    /**
     * Method to get baking recipes results
     * @return results through live data
     */
    public LiveData<BakingResult<List<BakingRecipeEntity>>> getBakingRecipes() {
        if (mBakingRecipes == null) {
            // get baking recipes from baking repository
            mBakingRecipes = mBakingRepository.getBakingRecipes();
        }
        // return baking recipes through live data
        return mBakingRecipes;
    }

    /**
     * Method to get baking ingredients through live data
     * @return baking ingredients through live data
     */
    public LiveData<List<BakingIngredient>> getBakingIngredients() {
        if (mBakingIngredients == null || mBakingIngredients.getValue() == null ||
                mBakingIngredients.getValue().get(0).getRecipeKey() != mRecipeKey) {
            // get baking ingredients from baking repository
            mBakingIngredients = mBakingRepository.getBakingIngredients(mRecipeKey);
        }
        // return baking ingredients through live data
        return mBakingIngredients;
    }

    /**
     * Method to get baking steps through live data
     * @return baking steps through live data
     */
    public LiveData<List<BakingStep>> getBakingSteps() {
        if (mBakingSteps == null || mBakingSteps.getValue() == null ||
                mBakingSteps.getValue().get(0).getStepKey() != mRecipeKey) {
            // get baking steps from baking repository
            mBakingSteps = mBakingRepository.getBakingSteps(mRecipeKey);
        }
        // return baking steps through live data
        return mBakingSteps;
    }

    /**
     * Setter for recipe key
     * @param recipeKey to set
     */
    public void setRecipeKey(int recipeKey) {mRecipeKey = recipeKey;}

    /**
     * Getter for recipe name
     * @return recipe name
     */
    public String getRecipeName(){return mRecipeName;}

    /**
     * Setter for recipe name
     * @param recipeName to set
     */
    public void setRecipeName(String recipeName) {mRecipeName = recipeName;}

    /**
     * Getter for baking Step
     * @return baking step
     */
    public BakingStep getBakingStep() {
        return mBakingStep;
    }

    /**
     * Method to get previous baking step from baking step list
     * @param stepId currently viewed
     * @return baking step or null if none found
     */
    public BakingStep getPreviousBakingStep(int stepId) {
        // Declare variables
        List<BakingStep> bakingSteps;
        // get baking step list
        bakingSteps = mBakingSteps.getValue();
        // get baking step from list
        if (bakingSteps != null) {
            for (int i = 1; i < bakingSteps.size(); i++) {
                if (bakingSteps.get(i).getId() == stepId) {
                    // return baking step if found
                    return bakingSteps.get(i - 1);
                }
            }
        }
        // return null if step not found
        return null;
    }

    /**
     * Method to get previous baking step from baking step list
     * @param stepId currently viewed
     * @return baking step or null if none found
     */
    public BakingStep getNextBakingStep(int stepId) {
        // Declare variables
        List<BakingStep> bakingSteps;
        // get baking step list
        bakingSteps = mBakingSteps.getValue();
        // get baking step from list
        if (bakingSteps != null) {
            for (int i = 0; i < bakingSteps.size() - 1; i++) {
                if (bakingSteps.get(i).getId() == stepId) {
                    // return baking step if found
                    return bakingSteps.get(i + 1);
                }
            }
        }
        // return null if step not found
        return null;
    }

    /**
     * Setter for baking step
     * @param bakingStep to set
     */
    public void setBakingStep(BakingStep bakingStep) {
        this.mBakingStep = bakingStep;
    }

    public int getLastStepId() {
        if (mBakingSteps.getValue() != null && mBakingSteps.getValue().size() > 0) {
            return mBakingSteps.getValue().get(mBakingSteps.getValue().size() - 1).getId();
        }
        return 0;
    }
}