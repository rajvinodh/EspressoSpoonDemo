package com.emmasuzuki.espressospoondemo.tests;

import org.junit.Assume;
import org.junit.Test;;
import com.emmasuzuki.espressospoondemo.R;
import com.emmasuzuki.espressospoondemo.pages.LoginPage;
import com.emmasuzuki.espressospoondemo.utils.annotations.CommonCategory;
import com.emmasuzuki.espressospoondemo.utils.annotations.DeviceEligibilityTest;
import com.emmasuzuki.espressospoondemo.utils.annotations.RealDevice;
import com.emmasuzuki.espressospoondemo.utils.annotations.Workshop;
import java.io.IOException;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class LoginActivityTest extends BaseTest {

    LoginPage loginPage;

    @DeviceEligibilityTest
    @Workshop
    @Test
    public void eligibility () throws InterruptedException {
//        if(getDeviceSerialID().equals("RZCW20RMPXM")) {
//            throw new RuntimeException("Custom Exception");
//        }

//        Thread.sleep(5000);
//        loginPage = new LoginPage();
//        loginPage.login("phonepe", "phonepe")
//                .getEmailField()
//                .check(matches(hasErrorText("Please enter your email")));
    }

    @CommonCategory
    @DeviceEligibilityTest
    @Test
    public void customEligibility () throws InterruptedException {

    }

    @RealDevice
    @Workshop
    @Test
    public void test_Invalid_Email_Error() throws IOException, InterruptedException {

        loginPage = new LoginPage();
        loginPage.login("phonepe", "phonepe")
                .getEmailField()
                .check(matches(hasErrorText("Please enter your email")));

    }

    @Workshop
    @Test
    public void test_Empty_Password_Error() {
        loginPage = new LoginPage();
        loginPage.login("test@test.com", "")
                .getPasswordField()
                    .check(matches(hasErrorText("Please enter your password")));
    }

    @Workshop
    @Test
    public void test_Set_Email_Mismatch_Error() {
        loginPage = new LoginPage();
        loginPage.login("espresso@spoon.com", "phonepe")
                .getErrorMsg()
                    .check(matches(allOf(isDisplayed()
                            , withText("Your email or password is wrong"))));

    }

    @Workshop
    @Test
    public void test_Set_Password_Mismatch_Error() {
        loginPage = new LoginPage();
        loginPage.login("phonepe@test.com", "wrong")
                .getErrorMsg()
                .check(matches(allOf(isDisplayed()
                        , withText("Your email or password is wrong"))));

    }

    @Workshop
    @Test
    public void test_Set_Correct_Cred() throws InterruptedException {
        loginPage = new LoginPage();
        loginPage.successfulLogin("phonepe@test.com", "phonepe")
                .getWelcomeMsg()
                .check(matches(allOf(isDisplayed()
                        , withText("Welcome! Everyone!"))));


        onView(withId(R.id.testme)).perform(click());

    }

    @RealDevice
    @Workshop
    @Test
    public void test_Set_Correct_Cred_failure() throws InterruptedException {
        loginPage = new LoginPage();
        loginPage.successfulLogin("phonepe@test.com", "phonepe1")
                .getWelcomeMsg()
                .check(matches(allOf(isDisplayed()
                        , withText("Welcome! Everyone!"))));
    }
}
