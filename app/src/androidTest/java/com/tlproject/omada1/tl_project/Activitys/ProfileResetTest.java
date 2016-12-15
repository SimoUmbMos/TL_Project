package com.tlproject.omada1.tl_project.Activitys;


import android.app.Instrumentation;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.tlproject.omada1.tl_project.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
public class ProfileResetTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void profileResetTest() {

        Instrumentation.ActivityMonitor mapAct = getInstrumentation()
                .addMonitor(MapsActivity.class.getName(), null, false);

        Instrumentation.ActivityMonitor profAct = getInstrumentation()
                .addMonitor(ProfileActivity.class.getName(), null, false);

        Instrumentation.ActivityMonitor logAct = getInstrumentation()
                .addMonitor(MainActivity.class.getName(), null, false);
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
        appCompatEditText2.perform(replaceText("testreset@a.com"), closeSoftKeyboard());

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
          MapsActivity mapActivity = (MapsActivity) getInstrumentation().waitForMonitorWithTimeout(mapAct, 5000);
        ViewInteraction button = onView(
                allOf(withId(R.id.btprofile), withText("Profile"),
                        withParent(allOf(withId(R.id.activity_map),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button.perform(click());

        /*ViewInteraction textView = onView(
                allOf(withId(R.id.lvl), withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.activity_profile),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                9),
                        isDisplayed()));
        textView.check(matches(withText("2")));*/
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

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.lvl), withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.activity_profile),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                9),
                        isDisplayed()));
        textView2.check(matches(withText("1")));

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btback), withText("Back"),
                        withParent(allOf(withId(R.id.activity_profile),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.btlogout), withText("logout"),
                        withParent(allOf(withId(R.id.activity_map),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.btlogout), withText("logout"),
                        withParent(allOf(withId(R.id.activity_map),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button3.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
