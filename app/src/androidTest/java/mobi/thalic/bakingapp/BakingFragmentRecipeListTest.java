package mobi.thalic.bakingapp;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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
    private static final String TITLE_TEXT = "Baking Time";

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

    @Test
    public void testBakingRecipeTitle() {
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(TITLE_TEXT)));
    }

}

