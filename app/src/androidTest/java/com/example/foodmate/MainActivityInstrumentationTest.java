package com.example.foodmate;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityInstrumentationTest {

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private MainActivity mMainActivity;
    @Before
    public void setUp()
    {
        mMainActivity = activityTestRule.getActivity();
    }
    @Test
    public void mainActivityTest() {
        onView(withId( R.id.nav_view)).perform(click());
        //        onView(withId( R.id.nav_recruiing)).perform(click());
//        onView(withId( R.id.nav_recruited)).perform(click());
//        onView(withId( R.id.nav_settings)).perform(click());
    }
}