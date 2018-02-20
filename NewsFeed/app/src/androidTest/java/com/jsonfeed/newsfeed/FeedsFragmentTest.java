package com.jsonfeed.newsfeed;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jsonfeed.newsfeed.activity.FeedActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FeedsFragmentTest {

    @Rule
    public ActivityTestRule<FeedActivity> mActivityRule = new ActivityTestRule<>(
            FeedActivity.class);


    @Test
    public void listView_checkItsText() {
        String itemElementText = mActivityRule.getActivity().getResources()
                .getString(R.string.first_item);
        onView(withText(itemElementText)).check(matches(not(isDisplayed())));
    }

}
