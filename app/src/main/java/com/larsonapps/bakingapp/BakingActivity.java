package com.larsonapps.bakingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.IdlingResource;

import android.os.Bundle;

import com.larsonapps.bakingapp.data.BakingRecipe;
import com.larsonapps.bakingapp.ui.main.BakingFragment;
import com.larsonapps.bakingapp.ui.main.BakingViewModel;
import com.larsonapps.bakingapp.utilities.BakingIdlingResource;

/**
 * Class for the MainActivity
 */
public class BakingActivity extends AppCompatActivity implements
        BakingFragment.OnListFragmentInteractionListener {
    // Declare variables
    BakingViewModel mBakingViewModel;

    // Variable will be null in production
    @Nullable
    private BakingIdlingResource mIdlingResource;

    /**
     * Getter for idling resource for testing
     * @return idlingResource
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        // if null create idling resource
        if (mIdlingResource == null) {
            mIdlingResource = new BakingIdlingResource();
        }
        return mIdlingResource;
    }

    /**
     * Method to create activity
     * @param savedInstanceState for saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baking_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BakingFragment.newInstance())
                    .commitNow();
        }
        // initialize the movie list view model
        mBakingViewModel = new ViewModelProvider(this).get(BakingViewModel.class);
        getIdlingResource();
    }

    /**
     * Click listener for recyclerView adapter
     * @param bakingRecipe of item clicked
     */
    @Override
    public void onListFragmentInteraction(BakingRecipe bakingRecipe) {

    }
}