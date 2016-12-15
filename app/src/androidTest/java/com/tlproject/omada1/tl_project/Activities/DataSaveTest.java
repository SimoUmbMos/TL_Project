package com.tlproject.omada1.tl_project.Activities;


import android.app.Instrumentation;
import android.support.test.espresso.ViewInteraction;
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
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DataSaveTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void dataSaveTest() {
        Instrumentation.ActivityMonitor mapAct = getInstrumentation()
                .addMonitor(MapsActivity.class.getName(), null, false);

        Instrumentation.ActivityMonitor profAct = getInstrumentation()
                .addMonitor(ProfileActivity.class.getName(), null, false);

        Instrumentation.ActivityMonitor logAct = getInstrumentation()
                .addMonitor(MainActivity.class.getName(), null, false);

        MainActivity logActivity =(MainActivity) getInstrumentation()
                .waitForMonitorWithTimeout(logAct, 5000);
        onView(withId(R.id.SignInEmail)).perform(click());

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.etEmail),
                withParent(allOf(withId(R.id.EmailAction), withParent(withId(R.id.activity_main)))),
                isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.etEmail), withParent(
                allOf(withId(R.id.EmailAction), withParent(withId(R.id.activity_main)))),
                isDisplayed()));
        appCompatEditText2.perform(replaceText("test123@test123.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.etPassword),
                withParent(allOf(withId(R.id.EmailAction), withParent(withId(R.id.activity_main)))),
                isDisplayed()));
        appCompatEditText3.perform(replaceText("test123"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.btProceed), withParent(
                allOf(withId(R.id.EmailAction), withParent(withId(R.id.activity_main)))),
                isDisplayed()));
        appCompatButton2.perform(click());

        MapsActivity mapActivity = (MapsActivity) getInstrumentation()
                .waitForMonitorWithTimeout(mapAct, 5000);

        ViewInteraction textView = onView(allOf(withId(R.id.usernamedisp), isDisplayed()));
        textView.check(matches(withText("test123")));

        ViewInteraction button = onView(allOf(withId(R.id.btprofile),
                withParent(allOf(withId(R.id.activity_map),withParent(withId(android.R.id.content)))),
                isDisplayed()));
        button.perform(click());

        ProfileActivity profActivity = (ProfileActivity) getInstrumentation()
                .waitForMonitorWithTimeout(profAct, 5000);

        ViewInteraction textView2 = onView(allOf(withId(R.id.questdesc), isDisplayed()));
        textView2.check(matches(withText("Tutorial")));

        ViewInteraction textView3 = onView(allOf(withId(R.id.usernameprofile), isDisplayed()));
        textView3.check(matches(withText("test123")));

        ViewInteraction textView4 = onView(allOf(withId(R.id.lvl), isDisplayed()));
        textView4.check(matches(withText("1")));

        ViewInteraction textView5 = onView(allOf(withId(R.id.curexp), isDisplayed()));
        textView5.check(matches(withText("0")));

        ViewInteraction textView6 = onView(allOf(withId(R.id.nextlvlexp), isDisplayed()));
        textView6.check(matches(withText("1000")));

        ViewInteraction appCompatButton3 = onView(allOf(withId(R.id.btback),
                withParent(allOf(withId(R.id.activity_profile), withParent(withId(android.R.id.content)))),
                isDisplayed()));
        appCompatButton3.perform(click());

        mapActivity = (MapsActivity) getInstrumentation().waitForMonitorWithTimeout(mapAct, 5000);

        ViewInteraction button2 = onView(allOf(withId(R.id.btaction),
                withParent(allOf(withId(R.id.activity_map),withParent(withId(android.R.id.content)))),
                isDisplayed()));
        button2.perform(click());

        mapActivity = (MapsActivity) getInstrumentation().waitForMonitorWithTimeout(mapAct, 5000);

        mapActivity = (MapsActivity) getInstrumentation().waitForMonitorWithTimeout(mapAct, 5000);

        ViewInteraction button3 = onView(allOf(withId(R.id.btprofile),
                withParent(allOf(withId(R.id.activity_map), withParent(withId(android.R.id.content)))),
                isDisplayed()));
        button3.perform(click());

        profActivity = (ProfileActivity) getInstrumentation().waitForMonitorWithTimeout(profAct, 15000);

        ViewInteraction textView7 = onView(allOf(withId(R.id.questdesc), isDisplayed()));
        textView7.check(matches(withText("telos")));

        ViewInteraction textView8 = onView(allOf(withId(R.id.nextlvlexp), isDisplayed()));
        textView8.check(matches(withText("2000")));

        ViewInteraction textView9 = onView(allOf(withId(R.id.curexp), isDisplayed()));
        textView9.check(matches(withText("0")));

        ViewInteraction textView10 = onView(allOf(withId(R.id.lvl), isDisplayed()));
        textView10.check(matches(withText("2")));

        ViewInteraction appCompatButton4 = onView(allOf(withId(R.id.btback),
                withParent(allOf(withId(R.id.activity_profile), withParent(withId(android.R.id.content)))),
                isDisplayed()));
        appCompatButton4.perform(click());

        mapActivity = (MapsActivity) getInstrumentation().waitForMonitorWithTimeout(mapAct, 5000);

        ViewInteraction button4 = onView(allOf(withId(R.id.btlogout),
                withParent(allOf(withId(R.id.activity_map), withParent(withId(android.R.id.content)))),
                isDisplayed()));
        button4.perform(click());

        logActivity =(MainActivity) getInstrumentation().waitForMonitorWithTimeout(logAct, 5000);

        ViewInteraction appCompatButton5 = onView(allOf(withId(R.id.SignInEmail),
                withText("Sign In with Email"), withParent(allOf(withId(R.id.SignInMenu),
                        withParent(withId(R.id.activity_main)))), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatEditText4 = onView(allOf(withId(R.id.etEmail),
                withParent(allOf(withId(R.id.EmailAction), withParent(withId(R.id.activity_main)))),
                isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(allOf(withId(R.id.etEmail),
                withParent(allOf(withId(R.id.EmailAction), withParent(withId(R.id.activity_main)))),
                isDisplayed()));
        appCompatEditText5.perform(replaceText("test123@test123.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(allOf(withId(R.id.etPassword),
                withParent(allOf(withId(R.id.EmailAction), withParent(withId(R.id.activity_main)))),
                isDisplayed()));
        appCompatEditText11.perform(replaceText("test123"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(allOf(withId(R.id.btProceed), isDisplayed()));
        appCompatButton6.perform(click());

        mapActivity = (MapsActivity) getInstrumentation().waitForMonitorWithTimeout(mapAct, 5000);

        ViewInteraction button5 = onView(allOf(withId(R.id.btprofile), withParent(allOf(withId
                (R.id.activity_map), withParent(withId(android.R.id.content)))), isDisplayed()));
        button5.perform(click());


        profActivity = (ProfileActivity) getInstrumentation().waitForMonitorWithTimeout(profAct, 5000);

        ViewInteraction textView12 = onView(allOf(withId(R.id.lvl), isDisplayed()));
        textView12.check(matches(withText("2")));

        ViewInteraction textView13 = onView(allOf(withId(R.id.usernameprofile), isDisplayed()));
        textView13.check(matches(withText("test123")));

        ViewInteraction appCompatButton7 = onView(allOf(withId(R.id.btback),
                withParent(allOf(withId(R.id.activity_profile), withParent(withId(android.R.id.content)))),
                isDisplayed()));
        appCompatButton7.perform(click());

        mapActivity = (MapsActivity) getInstrumentation().waitForMonitorWithTimeout(mapAct, 5000);

        onView(allOf(withId(R.id.btlogout), withParent(allOf(withId(R.id.activity_map),
                withParent(withId(android.R.id.content)))), isDisplayed())).perform(click());

        logActivity =(MainActivity) getInstrumentation().waitForMonitorWithTimeout(logAct, 5000);
    }
}
