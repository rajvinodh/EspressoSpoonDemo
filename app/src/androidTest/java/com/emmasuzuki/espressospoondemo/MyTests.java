package com.emmasuzuki.espressospoondemo;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.rule.GrantPermissionRule.grant;

import static org.hamcrest.core.AllOf.allOf;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MyTests {
    @Rule
    public ActivityTestRule<LoginActivity> activityActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = grant(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE);

    @Before
    public void before() {
        InstrumentationRegistry.getInstrumentation().getUiAutomation();
    }

    //@Test
    public void doScreenshot() {


        ViewHelpers.setupView(activityActivityTestRule.getActivity().getCurrentFocus()).layout();

        Screenshot.snapActivity(activityActivityTestRule.getActivity()).record();
    }

    public static Activity getActivity() {
        final Activity[] currentActivity = new Activity[1];
        onView(allOf(withId(android.R.id.content), isDisplayed())).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                if (view.getContext() instanceof Activity) {
                    Activity activity1 = ((Activity) view.getContext());
                    currentActivity[0] = activity1;
                }
            }
        });
        return currentActivity[0];
    }
}
