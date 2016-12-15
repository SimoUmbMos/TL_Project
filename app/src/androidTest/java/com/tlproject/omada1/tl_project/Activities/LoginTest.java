package com.tlproject.omada1.tl_project.Activities;


import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.tlproject.omada1.tl_project.R;
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
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loginTest() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(MapsActivity.class.getName(), null, false);

        onView(allOf(withId(R.id.SignInEmail), withText("Sign In with Email"), withParent(allOf(
                withId(R.id.SignInMenu), withParent(withId(R.id.activity_main)))), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.etEmail), withParent(allOf(withId(R.id.EmailAction),
                withParent(withId(R.id.activity_main)))), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.etEmail), withParent(allOf(withId(R.id.EmailAction),
                withParent(withId(R.id.activity_main)))), isDisplayed()))
                .perform(replaceText("test123@test123.com"), closeSoftKeyboard());

        onView(allOf(withId(R.id.etPassword), withParent(allOf(withId(R.id.EmailAction),
                withParent(withId(R.id.activity_main)))), isDisplayed()))
                .perform(replaceText("test123"), closeSoftKeyboard());

        onView(allOf(withId(R.id.btProceed), withText("Sign In"),
                withParent(allOf(withId(R.id.EmailAction), withParent(withId(R.id.activity_main)))),
                isDisplayed())).perform(click());

        MapsActivity nextActivity = (MapsActivity) getInstrumentation()
                .waitForMonitorWithTimeout(activityMonitor, 5000);

        onView(allOf(withId(R.id.usernamedisp), withText("test123"), isDisplayed()))
                .check(matches(withText("test123")));

        assertNotNull(nextActivity);

        onView(allOf(withId(R.id.btlogout), withParent(allOf(withId(R.id.activity_map),
                withParent(withId(android.R.id.content)))), isDisplayed())).perform(click());
    }
}
