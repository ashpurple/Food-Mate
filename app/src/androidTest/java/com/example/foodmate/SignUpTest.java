package com.example.foodmate;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {

    @Rule
    public ActivityTestRule<SignUpActivity> rule = new ActivityTestRule(SignUpActivity.class);

    @Test
    public void signUp(){
        Espresso.onView(withId(R.id.edit_email)).perform(ViewActions.typeText("jul1002@gachon.ac.kr"));
        Espresso.onView(withId(R.id.edit_email)).perform(ViewActions.typeText("foody"));
        Espresso.onView(withId(R.id.edit_password)).perform(ViewActions.typeText("foodmate"));
        Espresso.onView(withId(R.id.edit_confirmPassword)).perform(ViewActions.typeText("foodmate"));

        Espresso.onView(withId(R.id.btn_signup)).perform(click());
    }

}
