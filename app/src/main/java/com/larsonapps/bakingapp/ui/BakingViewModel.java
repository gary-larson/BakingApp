package com.larsonapps.bakingapp.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.larsonapps.bakingapp.data.BakingIngredient;
import com.larsonapps.bakingapp.data.BakingRecipeEntity;
import com.larsonapps.bakingapp.data.BakingRepository;
import com.larsonapps.bakingapp.data.BakingStep;
import com.larsonapps.bakingapp.utilities.BakingResult;

import java.util.List;

/**
 * Class to handle baking view model
 */
public class BakingViewModel extends AndroidViewModel {
    // Declare variables
    Application mApplication;
    BakingRepository mBakingRepository;
    private int mRecipeKey;
    private String mRecipeName;
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
}