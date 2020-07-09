package com.larsonapps.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.larsonapps.bakingapp.data.BakingRecipeEntity;
import com.larsonapps.bakingapp.data.BakingStep;
import com.larsonapps.bakingapp.ui.BakingDetailFragment;
import com.larsonapps.bakingapp.ui.BakingFragment;
import com.larsonapps.bakingapp.ui.BakingViewModel;

/**
 * Class for the MainActivity
 */
public class BakingActivity extends AppCompatActivity implements
        BakingFragment.OnListFragmentInteractionListener,
        BakingDetailFragment.OnListFragmentInteractionListener {
    // Declare constants
    private static final String BAKING_DETAIL_FRAGMENT = "BakingDetailFragment";
    // Declare variables
    BakingViewModel mBakingViewModel;
    FragmentManager mFragmentManager;

    /**
     * Method to create activity
     * @param savedInstanceState for saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking);
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.container, BakingFragment.newInstance())
                    .commitNow();
        }
        // initialize the movie list view model
        mBakingViewModel = new ViewModelProvider(this).get(BakingViewModel.class);
    }

    @Override
    public void onListFragmentInteraction(BakingRecipeEntity bakingRecipe) {
        mBakingViewModel.setRecipeKey(bakingRecipe.getId());
        mFragmentManager.beginTransaction()
                .replace(R.id.container, BakingDetailFragment.newInstance())
                .addToBackStack(BAKING_DETAIL_FRAGMENT)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(BakingStep bakingStep) {
        Toast.makeText(this, "Step " + bakingStep.getId() +
                " Description: " + bakingStep.getShortDescription(), Toast.LENGTH_LONG).show();
    }
}