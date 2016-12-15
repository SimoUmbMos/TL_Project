package com.tlproject.omada1.tl_project.Activities;


import android.app.Instrumentation;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tlproject.omada1.tl_project.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EditTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void editTest() {
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

        onView(withId(R.id.usernamedisp)).check(matches(withText("Test reset")));

        ViewInteraction button = onView(
                allOf(withId(R.id.btprofile), withText("Profile"),
                        withParent(allOf(withId(R.id.activity_map),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btedit), withText("Edit"),
                        withParent(allOf(withId(R.id.activity_profile),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction editText = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText2.perform(replaceText("Test edit"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());

        onView(withId(R.id.usernamedisp)).check(matches(withText("Test edit")));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.btlogout), withText("logout"),
                        withParent(allOf(withId(R.id.activity_map),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button2.perform(click());

    }

    @After
    public void resetProfileToLvl_2()
    {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child("ELzqwPZ2SafIfIxjvrUSsvLJ55l2;");
        dbref.child("username").setValue("Test reset");
    }
}
