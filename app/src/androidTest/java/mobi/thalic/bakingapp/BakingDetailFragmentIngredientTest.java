package mobi.thalic.bakingapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

import mobi.thalic.bakingapp.data.BakingRecipe;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class BakingDetailFragmentIngredientTest {
    // Declare constants
    private static final String INGREDIENTS_0 = "Graham Cracker crumbs - 2.0 CUP\n" +
            "unsalted butter, melted - 6.0 TBLSP\n" +
            "granulated sugar - 0.5 CUP\n" +
            "salt - 1.5 TSP\n" +
            "vanilla - 5.0 TBLSP\n" +
            "Nutella or other chocolate-hazelnut spread - 1.0 K\n" +
            "Mascapone Cheese(room temperature) - 500.0 G\n" +
            "heavy cream(cold) - 1.0 CUP\n" +
            "cream cheese(softened) - 4.0 OZ";
    private static final String DISPLAYED_TEXT = "Nutella Pie";

    /**
     * Method to set create rule
     */
    @Rule
    public ActivityScenarioRule<BakingActivity> mActivityTestRule = new
            ActivityScenarioRule<>(BakingActivity.class);

    @Test
    public void testIngredientsInTextView () {
        onView(withText(DISPLAYED_TEXT)).perform(click());
        onView(withText(INGREDIENTS_0)).check(matches(isDisplayed()));
    }
}
