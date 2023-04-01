package com.emmasuzuki.espressospoondemo.tests;

import android.Manifest;
import android.app.Activity;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.uiautomator.UiDevice;

import com.emmasuzuki.espressospoondemo.LoginActivity;
import com.squareup.spoon.SpoonRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static android.Manifest.permission.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.rule.GrantPermissionRule.grant;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import io.qameta.allure.android.rules.LogcatRule;
import io.qameta.allure.android.rules.ScreenshotRule;

public class BaseTest {

    public ActivityScenario<LoginActivity> mActivityScenario;

    @Rule
    public TestName testName = new TestName();

    @Rule
    public GrantPermissionRule permissionRule =
            grant(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = grant(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE);

    @Rule
    public SpoonRule spoonRule = new SpoonRule();

//    @Rule public RuleChain ruleChain = RuleChain.outerRule(new LogcatRule())
//            .around(new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot-failure"));

    @Rule public ScreenshotRule ruleChain = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot-failure");

    @Rule
    public TestRule testWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            spoonRule.screenshot(getActivity(), "Test_failed");
        }

        @Override
        protected void finished(Description description) {
            mActivityScenario.close();
        }
    };

    @Before
    public void launch() {
        mActivityScenario =  ActivityScenario.launch(LoginActivity.class);
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

    protected static Activity tryAcquireScenarioActivity(ActivityScenario activityScenario) {
        Semaphore activityResource = new Semaphore(0);
        Activity[] scenarioActivity = new Activity[1];
        activityScenario.onActivity(activity -> {
            scenarioActivity[0] = activity;
            activityResource.release();
        });
        try {
            activityResource.tryAcquire(15000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to acquire activity scenario semaphore");
        }
        if(scenarioActivity[0] == null) {
            throw new RuntimeException("Scenario Activity should be non-null");
        }
        return scenarioActivity[0];
    }

    public static String getDeviceSerialID() {
        String udidNo;
        try {
            UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            String udid = mDevice.executeShellCommand("getprop ro.serialno");
            udidNo = udid.replace("\n", " ").trim();
        } catch (SecurityException | IOException e) {
            return "not found";
        }
        return udidNo;
    }

}
