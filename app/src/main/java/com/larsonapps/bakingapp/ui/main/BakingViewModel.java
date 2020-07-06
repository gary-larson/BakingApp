package com.larsonapps.bakingapp.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.larsonapps.bakingapp.data.BakingRecipe;
import com.larsonapps.bakingapp.data.BakingRepository;
import com.larsonapps.bakingapp.utilities.BakingIdlingResource;
import com.larsonapps.bakingapp.utilities.BakingResult;

import java.util.List;

public class BakingViewModel extends AndroidViewModel {
    // Declare variables
    Application mApplication;
    BakingRepository mBakingRepository;
    LiveData<BakingResult<List<BakingRecipe>>> mBakingRecipes;

    /**
     * Constructor foe baking view model
     * @param application to be used in the repository
     */
    public BakingViewModel (Application application) {
        super(application);
        this.mApplication = application;
        mBakingRepository = new BakingRepository(mApplication);
    }

    public LiveData<BakingResult<List<BakingRecipe>>> getBakingRecipes (
            BakingIdlingResource bakingIdlingResource) {
        if (mBakingRecipes == null) {
            mBakingRecipes = mBakingRepository.getBakingRecipes(bakingIdlingResource);
        }
        return mBakingRecipes;
    }
}