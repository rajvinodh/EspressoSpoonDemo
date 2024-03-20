package com.emmasuzuki.espressospoondemo.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.platform.app.InstrumentationRegistry;

import com.emmasuzuki.espressospoondemo.LoginActivity;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CustomSS {


//    @Test
    public void testing() throws InterruptedException {
        Thread.sleep(10000);

        Activity activity = getActivity(LoginActivity.class);
        activity.findViewById(android.R.id.content).post(() -> {
            // Now the layout is complete, you can capture the screenshot
            Bitmap screenshot = captureScreenshot(activity);

            try {
                File screenshotFile = new File(activity.getExternalFilesDir(null), "screenshot.png");
                saveBitmapToFile(screenshot, screenshotFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void saveBitmapToFile(Bitmap bitmap, File file) throws IOException {
        // Make sure the parent directory exists
        file.getParentFile().mkdirs();

        // Create a file output stream to write to the file
        FileOutputStream outputStream = new FileOutputStream(file);

        try {
            // Compress the bitmap to PNG format (you can change this to JPEG or other formats as needed)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            // Flush and close the output stream
            outputStream.flush();
            outputStream.close();
        } finally {
            // Ensure the output stream is closed in case of an exception
            outputStream.close();
        }
    }

    public static <T extends Activity> T getActivity(Class<T> activityClass) {
        final T[] activity = (T[]) new Activity[1];

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            try {
                activity[0] = activityClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate activity", e);
            }
        });

        return activity[0];
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

    private Bitmap captureScreenshot(Activity activity) {
        // Get the root view of the activity
        View rootView = activity.findViewById(android.R.id.content);

        // Create a Bitmap with the size of the root view
        Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a Canvas with the Bitmap
        Canvas canvas = new Canvas(bitmap);

        // Draw the entire view hierarchy onto the Canvas
        rootView.draw(canvas);

        return bitmap;
    }

    // Utility method to save Bitmap to a file (optional)
    private void saveBitmapToFile(Bitmap bitmap, String filename) {
        // Implementation to save bitmap to file
    }
}
