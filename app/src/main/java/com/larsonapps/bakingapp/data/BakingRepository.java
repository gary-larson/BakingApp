package com.larsonapps.bakingapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.larsonapps.bakingapp.R;
import com.larsonapps.bakingapp.utilities.BakingExecutor;
import com.larsonapps.bakingapp.utilities.BakingIdlingResource;
import com.larsonapps.bakingapp.utilities.BakingJsonUtilities;
import com.larsonapps.bakingapp.utilities.BakingNetworkUtilities;
import com.larsonapps.bakingapp.utilities.BakingResult;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Interface for the callback
 * @param <T>
 */
interface RepositoryCallback<T> {
    void onComplete(BakingResult<T> bakingResult);
}

public class BakingRepository {
    // Declare Variables
    private final BakingExecutor executor;
    private final Application mApplication;
    private final String mErrorMessage;
    private MutableLiveData<BakingResult<List<BakingRecipe>>> mBakingResult;

    public BakingRepository (Application application) {
        // Initialize variables
        mApplication = application;
        mErrorMessage = mApplication.getString(R.string.error_message);
        executor = new BakingExecutor();
        mBakingResult = new MutableLiveData<>();
    }

    /**
     * Method to retrieve the Baking list from the internet
     * @param callback to handle results
     */
    public void retrieveBakingList (
            final RepositoryCallback<List<BakingRecipe>> callback
    ) {
        executor.execute(() -> {
            // build url
            URL bakingRequestUrl = BakingNetworkUtilities.buildUrl();

            try {
                // attempt to get baking information
                String jsonBakingRecipeResponse = BakingNetworkUtilities
                        .getResponseFromHttpUrl(bakingRequestUrl);
                // if null cancel task (Unknown error)
                if (jsonBakingRecipeResponse == null) {
                    BakingResult<List<BakingRecipe>> errorResult = new BakingResult.Error<>(mErrorMessage);
                    callback.onComplete(errorResult);
                }
                // return Json decoded movie Information
                List<BakingRecipe> bakingRecipes = BakingJsonUtilities.getBakingRecipes(jsonBakingRecipeResponse);
                BakingResult<List<BakingRecipe>> bakingResult;
                if (bakingRecipes == null) {
                    bakingResult = new BakingResult.Error<>(mErrorMessage);
                } else {
                    bakingResult = new BakingResult.Success<>(bakingRecipes);
                }
                callback.onComplete(bakingResult);
            } catch (Exception e) {
                e.printStackTrace();
                // in case of an error return null
                BakingResult<List<BakingRecipe>> errorResult = new BakingResult.Error<>(mErrorMessage);
                callback.onComplete(errorResult);
            }
        });
    }

    public LiveData<BakingResult<List<BakingRecipe>>> getBakingRecipes (
            BakingIdlingResource bakingIdlingResource) {
        if (bakingIdlingResource != null) {
            bakingIdlingResource.setIdleState(false);
        }
        retrieveBakingList(bakingResult -> mBakingResult.postValue(bakingResult));
        return mBakingResult;
    }
}
