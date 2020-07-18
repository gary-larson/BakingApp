package mobi.thalic.bakingapp;

import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
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
    private static final String TABLET_STEP_2 = DISPLAYED_TEXT + " - " + STEP_2;

    private boolean isTwoPane;

    /**
     * Method to set create rule
     */
    @Rule
    public ActivityScenarioRule<BakingActivity> mActivityTestRule = new
            ActivityScenarioRule<>(BakingActivity.class);

    @Before
    public void getTwoPaneMode() {
        Context context = ApplicationProvider.getApplicationContext();
        isTwoPane = context.getResources().getBoolean(R.bool.is_two_pane);
    }

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
        if (isTwoPane) {
            onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                    .check(matches(withText(TABLET_STEP_2)));
        } else {
            onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                    .check(matches(withText(STEP_2)));
        }
    }
}
