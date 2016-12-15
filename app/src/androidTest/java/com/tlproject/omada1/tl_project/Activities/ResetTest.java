package com.tlproject.omada1.tl_project.Activities;


import android.app.Instrumentation;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tlproject.omada1.tl_project.R;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ResetTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void resetTest() {
        Instrumentation.ActivityMonitor mapAct = getInstrumentation()
                .addMonitor(MapsActivity.class.getName(), null, false);

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.SignInEmail), withText("Sign In with Email"),
                        withParent(allOf(withId(R.id.SignInMenu),
                                withParent(withId(R.id.activity_main)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etEmail),
                        withParent(allOf(withId(R.id.EmailAction),
                                withParent(withId(R.id.activity_main)))),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etEmail),
                        withParent(allOf(withId(R.id.EmailAction),
                                withParent(withId(R.id.activity_main)))),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("s@s.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.etPassword),
                        withParent(allOf(withId(R.id.EmailAction),
                                withParent(withId(R.id.activity_main)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btProceed), withText("Sign In"),
                        withParent(allOf(withId(R.id.EmailAction),
                                withParent(withId(R.id.activity_main)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        MapsActivity mapActivity = (MapsActivity) getInstrumentation().waitForMonitorWithTimeout(mapAct, 3000);

        ViewInteraction button = onView(
                allOf(withId(R.id.btprofile), withText("Profile"),
                        withParent(allOf(withId(R.id.activity_map),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button.perform(click());

        onView(withId(R.id.lvl)).check(matches(withText("2")));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btreset), withText("Reset"),
                        withParent(allOf(withId(R.id.activity_profile),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_yes), withText("Yes"), isDisplayed()));
        appCompatButton4.perform(click());

        MapsActivity mapActivity2 = (MapsActivity) getInstrumentation().waitForMonitorWithTimeout(mapAct, 1000);

        ViewInteraction button2 = onView(
                allOf(withId(R.id.btprofile), withText("Profile"),
                        withParent(allOf(withId(R.id.activity_map),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button2.perform(click());

        onView(withId(R.id.lvl)).check(matches(withText("1")));

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btback), withText("Back"),
                        withParent(allOf(withId(R.id.activity_profile),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.btlogout), withText("logout"),
                        withParent(allOf(withId(R.id.activity_map),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button3.perform(click());

    }

    @After
    public void resetProfileToLvl_2()
    {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child("ELzqwPZ2SafIfIxjvrUSsvLJ55l2;");
        dbref.child("lvl").setValue("2");
    }

}
