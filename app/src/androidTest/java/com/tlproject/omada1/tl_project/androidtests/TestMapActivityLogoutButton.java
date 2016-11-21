package com.tlproject.omada1.tl_project.androidtests;

import android.app.Instrumentation;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tlproject.omada1.tl_project.Activitys.LoginActivity;
import com.tlproject.omada1.tl_project.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by sorar on 21/11/2016.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestMapActivityLogoutButton {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule
            = new ActivityTestRule<>(LoginActivity.class);
    @Test
    public void checkLogOut() {
        String String_To_Type = "test123";

        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(LoginActivity.class.getName(), null, false);

        // Press the button.
        onView(withId(R.id.username)).perform(typeText(String_To_Type),
                closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(String_To_Type),
                closeSoftKeyboard());
        onView(withId(R.id.btlogin)).perform(click());

        onView(withId(R.id.btlogout)).perform(click());

        //Watch for the timeout
        LoginActivity nextActivity = (LoginActivity) getInstrumentation()
                .waitForMonitorWithTimeout(activityMonitor, 5000);

        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }
}

