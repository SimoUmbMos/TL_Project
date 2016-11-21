package com.tlproject.omada1.tl_project.androidtests;

import org.junit.runner.RunWith;
import org.junit.Test;

import android.support.test.filters.SdkSuppress;
import org.junit.Rule;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.app.Instrumentation;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

import com.tlproject.omada1.tl_project.Activitys.LoginActivity;
import com.tlproject.omada1.tl_project.Activitys.RegisterActivity;
import com.tlproject.omada1.tl_project.R;



/**
 * Created by sorar on 17/11/2016.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestLoginActivityRegisterButton {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule
            = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void checkRegistOpens() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(RegisterActivity.class.getName(), null, false);

        // Press the button.
        onView(withId(R.id.btregist)).perform(click());

        //Watch for the timeout
        RegisterActivity nextActivity = (RegisterActivity) getInstrumentation()
                .waitForMonitorWithTimeout(activityMonitor, 5000);

        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity .finish();
    }
}
