package com.larsonapps.bakingapp;

import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.larsonapps.bakingapp.data.BakingRecipe;
import com.larsonapps.bakingapp.ui.main.BakingFragment;
import com.larsonapps.bakingapp.ui.main.BakingRecipeRecyclerViewAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class BakingFragmentRecipeListTest {
    // TODO write test for retrieval of the recipe list
    @Rule
    public ActivityTestRule<BakingActivity> mActivityTestRule = new
            ActivityTestRule<>(BakingActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mActivityTestRule.getActivity().runOnUiThread(new Runnable () {
              @Override
              public void run() {
                  FragmentManager mFragmentManager = mActivityTestRule.getActivity().getSupportFragmentManager();
                  mFragmentManager.beginTransaction()
                          .replace(R.id.container, BakingFragment.newInstance())
                          .commitNow();
              }
          });
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void testGetBakingRecipe() {
        onView(withId(R.id.rv_recipe_list)).perform(click());
}

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}

