package com.larsonapps.bakingapp.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.larsonapps.bakingapp.data.BakingRecipe;
import com.larsonapps.bakingapp.data.BakingRepository;
import com.larsonapps.bakingapp.utilities.BakingResult;

import java.util.List;

public class BakingViewModel extends AndroidViewModel {
    // Declare variables
    Application mApplication;
    BakingRepository mBakingRepository;

    /**
     * Constructor foe baking view model
     * @param application to be used in the repository
     */
    public BakingViewModel (Application application) {
        super(application);
        this.mApplication = application;
        mBakingRepository = new BakingRepository(mApplication);
    }

    public BakingResult<List<BakingRecipe>> getBakingRecipes () {
        return mBakingRepository.getBakingRecipes();
    }
}