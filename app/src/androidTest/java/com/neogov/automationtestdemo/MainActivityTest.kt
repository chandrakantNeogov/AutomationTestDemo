package com.neogov.automationtestdemo

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @Test
    fun login_withInvalidEmail_showsEmailError() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.emailInput)).perform(typeText("invalid-email"), closeSoftKeyboard())
        onView(withId(R.id.passwordInput)).perform(typeText("validpassword"), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.emailInput)).check(matches(hasErrorText("Invalid email")))
    }

    @Test
    fun login_withInvalidPassword_showsPasswordError() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.emailInput)).perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordInput)).perform(typeText("short"), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.passwordInput)).check(matches(hasErrorText("Invalid password")))
    }

    @Test
    fun login_withValidCredentials_clearsInputFields() {
        ActivityScenario.launch(MainActivity::class.java)

       onView(withId(R.id.emailInput)).perform(replaceText("chandra@gmail.com"), closeSoftKeyboard())
       onView(withId(R.id.passwordInput)).perform(replaceText("validpassword"), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        // Wait for UI to settle
        Espresso.onIdle()

        // Verify that the input fields are cleared
        onView(withId(R.id.emailInput)).check(matches(withText("")))
        onView(withId(R.id.passwordInput)).check(matches(withText("")))
    }
}