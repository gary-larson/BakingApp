package mobi.thalic.bakingapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class BakingFragmentStepTest {
    // Declare constants
    private static final String STEP_0 = "Prep the cookie crust.";
    private static final String DISPLAYED_TEXT = "Nutella Pie";

    /**
     * Method to set create rule
     */
    @Rule
    public ActivityScenarioRule<BakingActivity> mActivityTestRule = new
            ActivityScenarioRule<>(BakingActivity.class);

    @Test
    public void testStepInTextView () {
        onView(withText(DISPLAYED_TEXT)).perform(click());
        onView(withText(STEP_0)).check(matches(isDisplayed()));
    }
}
