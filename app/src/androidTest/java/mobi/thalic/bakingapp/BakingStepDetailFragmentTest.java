package mobi.thalic.bakingapp;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class BakingStepDetailFragmentTest {
    // Declare constants
    private static final String STEP_2_DISPLAYED = "2. Whisk the graham cracker crumbs, 50 grams " +
            "(1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the " +
            "melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together " +
            "until evenly mixed.";
    private static final String STEP_2 = "Prep the cookie crust.";
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
        onView(withText(STEP_2)).perform(click());
        onView(withText(STEP_2_DISPLAYED)).check(matches(isDisplayed()));
    }

    @Test
    public void testStepTitle() {
        onView(withText(DISPLAYED_TEXT)).perform(click());
        onView(withText(STEP_2)).perform(click());
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(STEP_2)));
    }
}
