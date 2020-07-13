package mobi.thalic.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import mobi.thalic.bakingapp.R;
import mobi.thalic.bakingapp.data.BakingRecipeEntity;
import mobi.thalic.bakingapp.data.BakingStep;
import mobi.thalic.bakingapp.viewmodel.BakingViewModel;

/**
 * Class for the MainActivity
 */
public class BakingActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener,
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
        // set content view
        setContentView(R.layout.activity_baking);
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
        // replace fragment with baking detail fragment
        mFragmentManager.beginTransaction()
                .replace(R.id.container, BakingDetailFragment.newInstance())
                .addToBackStack(BAKING_DETAIL_FRAGMENT)
                .commit();
    }

    /**
     * Method to handle clicks of baking steps in the baking detail fragment recyclerview
     * @param bakingStep clicked on
     */
    @Override
    public void onListFragmentInteraction(BakingStep bakingStep) {
        Toast.makeText(this, "Step " + bakingStep.getId() +
                " Description: " + bakingStep.getShortDescription(), Toast.LENGTH_LONG).show();
    }
}