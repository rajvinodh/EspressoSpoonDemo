package com.emmasuzuki.espressospoondemo

import android.R
import android.app.Activity
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.karumi.shot.ScreenshotTest
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test

class VinodhTest : ScreenshotTest {

    @get:Rule
    var activityActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun activityTest() {
        Thread.sleep(10000)
        getActivity()?.let { compareScreenshot(it, 4000) }
    }

    fun getActivity(): Activity? {
        val currentActivity = arrayOfNulls<Activity>(1)
        Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.content), ViewMatchers.isDisplayed())).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController, view: View) {
                if (view.context is Activity) {
                    val activity1 = view.context as Activity
                    currentActivity[0] = activity1
                }
            }
        })
        return currentActivity[0]
    }

}