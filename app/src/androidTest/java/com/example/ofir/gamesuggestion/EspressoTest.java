package com.example.ofir.gamesuggestion;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by ofir on 1/12/2018.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest
{
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<Login> mActivityRule = new ActivityTestRule<>(Login.class);

    @Before
    public void initValidString()
    {
        // Specify a valid string.
        mStringToBetyped = "Espresso";
    }

    @Test
    public void changeText_sameActivity()
    {
        // Type text and then press the button.
        onView(withId(R.id.username)).perform(typeText(mStringToBetyped), closeSoftKeyboard());

        onView(withId(R.id.sign_in)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.error))
                .check(matches(withText("Username And/Or Password Is Incorrect")));
    }
}
