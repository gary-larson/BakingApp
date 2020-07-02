package com.larsonapps.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.larsonapps.bakingapp.data.BakingRecipe;
import com.larsonapps.bakingapp.ui.main.BakingFragment;
import com.larsonapps.bakingapp.ui.main.BakingViewModel;

public class BakingActivity extends AppCompatActivity implements
        BakingFragment.OnListFragmentInteractionListener {
    BakingViewModel mBakingViewModel;

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
    }

    @Override
    public void onListFragmentInteraction(BakingRecipe bakingRecipe) {

    }
}