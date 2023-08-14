package com.emmasuzuki.espressospoondemo.tests;

import com.emmasuzuki.espressospoondemo.R;
import com.emmasuzuki.espressospoondemo.pages.HomePage;
import com.emmasuzuki.espressospoondemo.pages.LoginPage;
import com.emmasuzuki.espressospoondemo.utils.annotations.Device;
import com.emmasuzuki.espressospoondemo.utils.annotations.RealDevice;
import com.emmasuzuki.espressospoondemo.utils.annotations.Workshop;

import org.junit.Test;

import java.io.IOException;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

//@Device({"CTLID18111600932"})
public class LoginActivityTest3 extends BaseTest {

    LoginPage loginPage;
    HomePage homePage;

//    @Device({"CTLID18111600932"})
//    @Workshop
    @Test
    public void test_Invalid_Email_Error() throws IOException, InterruptedException {

        loginPage = new LoginPage();
        loginPage.login("phonepe", "phonepe")
                .getEmailField()
                .check(matches(hasErrorText("Please enter your email")));

    }

    @Test
    public void test_Empty_Password_Error() {
        loginPage = new LoginPage();
        loginPage.login("test@test.com", "")
                .getPasswordField()
                .check(matches(hasErrorText("Please enter your password")));
    }

    @Test
    public void test_Set_Email_Mismatch_Error() {
        loginPage = new LoginPage();
        loginPage.login("espresso@spoon.com", "phonepe")
                .getErrorMsg()
                .check(matches(allOf(isDisplayed()
                        , withText("Your email or password is wrong"))));

    }

    @Test
    public void test_Set_Password_Mismatch_Error() {
        loginPage = new LoginPage();
        loginPage.login("phonepe@test.com", "wrong")
                .getErrorMsg()
                .check(matches(allOf(isDisplayed()
                        , withText("Your email or password is wrong"))));

    }


    @RealDevice
//    @Workshop
    @Test
    public void test_Set_Correct_Cred() throws InterruptedException {
        loginPage = new LoginPage();
        loginPage.successfulLogin("phonepe@test.com", "phonepe")
                .getWelcomeMsg()
                .check(matches(allOf(isDisplayed()
                        , withText("Welcome! Everyone!"))));


        onView(withId(R.id.testme)).perform(click());
    }
}
