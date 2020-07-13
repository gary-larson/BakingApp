package mobi.thalic.bakingapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import mobi.thalic.bakingapp.BakingActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Class to test use case getting baking recipe information to displaying information in recyclerview
 * tests BakingExecutor, BakingResult, BakingNetworkUtilities.getResponseFromHttpUrl,
 * BakingJsonUtilities.getBakingRecipes, BakingRepository.getBakingRecipes,
 * BakingRepository.retrieveBakingList, BakingViewmodel.getBakingRecipes,
 * BakingRecipeRecyclerViewAdapter, BakingFragment and BakingActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BakingFragmentRecipeListTest {
    // Declare variables
    private static final String DISPLAYED_TEXT = "Nutella Pie";

    /**
     * Method to set create rule
     */
    @Rule
    public ActivityScenarioRule<BakingActivity> mActivityTestRule = new
            ActivityScenarioRule<>(BakingActivity.class);

    /**
     * Method to perform test
     */
    @Test
    public void testGetBakingRecipe() {
        onView(withText(DISPLAYED_TEXT)).check(matches(isDisplayed()));
    }

}

