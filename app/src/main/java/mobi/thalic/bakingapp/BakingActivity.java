package mobi.thalic.bakingapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import mobi.thalic.bakingapp.data.BakingRecipeEntity;
import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.databinding.ActivityBakingBinding;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * Class for the MainActivity
 */
public class BakingActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener,
        BakingFragment.OnListFragmentInteractionListener,
        StepDescriptionListFragment.OnListFragmentInteractionListener {
    // Declare constants
    private static final String BAKING_DETAIL_FRAGMENT = "BakingDetailFragment";
    private static final String BAKING_STEP_DETAIL_FRAGMENT = "BakingStepDetailFragment";
    // Declare variables
    BakingViewModel mBakingViewModel;
    ActivityBakingBinding binding;
    FragmentManager mFragmentManager;


    /**
     * Method to create activity
     * @param savedInstanceState for saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // enable view binding
        binding = ActivityBakingBinding.inflate(getLayoutInflater());
        // set content view
        setContentView(binding.getRoot());
        // get fragment manager
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);
        displayUpButton();
        if (savedInstanceState == null) {
            // Set starting fragment
            mFragmentManager.beginTransaction()
                    .replace(R.id.container, BakingFragment.newInstance())
                    .commitNow();
        }
        // initialize the baking view model
        mBakingViewModel = new ViewModelProvider(this).get(BakingViewModel.class);
    }

    /**
     * Method to display upo button if back stack is greater than 0
     */
    private void displayUpButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(
                    mFragmentManager.getBackStackEntryCount() > 0);
        }
    }

    /**
     * Method to deal with a change in the back stack
     */
    @Override
    public void onBackStackChanged() {
        displayUpButton();
    }

    /**
     * Method to process up button
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }

    /**
     * Method to handle clicks of baking recipes in the baking fragment recyclerview
     * @param bakingRecipe clicked on
     */
    @Override
    public void onListFragmentInteraction(BakingRecipeEntity bakingRecipe) {
        // set recipe key and name in baking view model
        mBakingViewModel.setRecipeKey(bakingRecipe.getId());
        mBakingViewModel.setRecipeName(bakingRecipe.getName());
        mFragmentManager.beginTransaction()
                .replace(binding.container.getId(), BakingDetailFragment.newInstance())
                .addToBackStack(BAKING_DETAIL_FRAGMENT)
                .commit();
    }

    /**
          * Method to handle clicks of baking steps in the baking detail fragment recyclerview
          * @param bakingStep that was clicked
          */
    @Override
    public void onListFragmentInteraction(BakingStep bakingStep) {
        mBakingViewModel.setBakingStep(bakingStep);
        mFragmentManager.beginTransaction()
                .replace(binding.container.getId(), BakingStepDetailFragment.newInstance())
                .addToBackStack(BAKING_STEP_DETAIL_FRAGMENT)
                .commit();
    }
}