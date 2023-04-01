package com.emmasuzuki.espressospoondemo.tests;

import static androidx.test.core.graphics.BitmapStorage.writeToTestStorage;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.screenshot.ViewInteractionCapture.captureToBitmap;

import static org.hamcrest.Matchers.allOf;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import com.emmasuzuki.espressospoondemo.HomeActivity;
import com.emmasuzuki.espressospoondemo.R;
import com.emmasuzuki.espressospoondemo.utils.annotations.SS;
import org.junit.Test;

import java.io.IOException;

public class ScreenshotExampleTest extends BaseTest {

    public ActivityScenario<HomeActivity> homeActivityActivityScenario;

    @SS
    @Test
    public void loginActivity() throws IOException, InterruptedException, ClassNotFoundException {
        Class<Activity> dynamicClass = (Class<Activity>) Class.forName("com.emmasuzuki.espressospoondemo.LoginActivity");

        ActivityScenario dynamicActivityActivityScenario = ActivityScenario.launch(dynamicClass);
        try {
            Thread.sleep(5000);
            writeToTestStorage(captureToBitmap(onView(withId(R.id.email))), getClass().getSimpleName() + "_" + testName.getMethodName());
        } finally {
            dynamicActivityActivityScenario.close();
        }
    }

//    @SS
    @Test
    public void homeActivity() throws IOException, InterruptedException, ClassNotFoundException {
        Class<Activity> dynamicClass = (Class<Activity>) Class.forName("com.emmasuzuki.espressospoondemo.HomeActivity");

        ActivityScenario dynamicActivityActivityScenario = ActivityScenario.launch(dynamicClass);
        try {
            Thread.sleep(5000);
            writeToTestStorage(captureToBitmap(onView(isRoot())), getClass().getSimpleName() + "_" + testName.getMethodName());
        } finally {
            dynamicActivityActivityScenario.close();
        }
    }

//    @SS
    @Test
    public void menuActivity() throws IOException, InterruptedException, ClassNotFoundException {
        Class<Activity> dynamicClass = (Class<Activity>) Class.forName("com.example.reusable_activities.MenuActivity");

        ActivityScenario dynamicActivityActivityScenario = ActivityScenario.launch(dynamicClass);

        Activity activity = tryAcquireScenarioActivity(dynamicActivityActivityScenario);
        View rect = activity.getWindow().getDecorView();
        int h = rect.getHeight();
        int w = rect.getWidth();

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        int he = device.getDisplayHeight();
        int wi = device.getDisplayWidth();
        try {
            Thread.sleep(5000);
            writeToTestStorage(captureToBitmap(onView(withId(android.R.id.content))), getClass().getSimpleName() + "_" + testName.getMethodName());

//            androidx.test.core.view.ViewCapture.captureToBitmap()
        } finally {
            dynamicActivityActivityScenario.close();
        }
    }
}
