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

public class BakingViewModel extends AndroidViewModel {
    // Declare variables
    Application mApplication;
    BakingRepository mBakingRepository;
    private int mRecipeKey;
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

    public LiveData<BakingResult<List<BakingRecipeEntity>>> getBakingRecipes() {
        if (mBakingRecipes == null) {
            mBakingRecipes = mBakingRepository.getBakingRecipes();
        }
        return mBakingRecipes;
    }

    public LiveData<List<BakingIngredient>> getBakingIngredients() {
        if (mBakingIngredients == null || mBakingIngredients.getValue() == null ||
                mBakingIngredients.getValue().get(0).getRecipeKey() != mRecipeKey) {
            mBakingIngredients = mBakingRepository.getBakingIngredients(mRecipeKey);
        }
        return mBakingIngredients;
    }

    public LiveData<List<BakingStep>> getBakingSteps() {
        if (mBakingSteps == null) {
            mBakingSteps = mBakingRepository.getBakingSteps(mRecipeKey);
        }
        return mBakingSteps;
    }

    public void setRecipeKey(int recipeKey) {mRecipeKey = recipeKey;}
}