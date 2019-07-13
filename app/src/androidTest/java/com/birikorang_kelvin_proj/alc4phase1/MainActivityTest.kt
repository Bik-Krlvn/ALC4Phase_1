package com.birikorang_kelvin_proj.alc4phase1

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityTestRule =
        ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
        Intents.init()
    }

    @Test
    fun navigateToProfileActivity() {
        onView(withId(R.id.btn_profile)).perform(click())
        intended(hasComponent(ProfileActivity::class.java.name))
        onView(withId((R.id.tv_name))).check(matches(withText(TEXT_NAME)))
        onView(withId((R.id.tv_track))).check(matches(withText(TEXT_TRACK)))
        onView(withId((R.id.tv_country))).check(matches(withText(TEXT_COUNTRY)))
        onView(withId((R.id.tv_contact))).check(matches(withText(TEXT_CONTACT)))
        onView(withId((R.id.tv_email))).check(matches(withText(TEXT_EMAIL)))
        onView(withId((R.id.tv_username))).check(matches(withText(TEXT_USERNAME)))
    }

    @Test
    fun navigateToAboutActivity() {
        onView(withId(R.id.btn_about)).perform(click())
        intended(hasComponent(AboutActivity::class.java.name))
        onView(withText("Warning"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Suppress("SpellCheckingInspection")
    companion object {
        const val TEXT_NAME = "Kelvin Birikorang"
        const val TEXT_TRACK = "Android"
        const val TEXT_EMAIL = "kelvinbirikorang@rocketmail.com"
        const val TEXT_COUNTRY = "Ghana"
        const val TEXT_CONTACT = "+233548977159"
        const val TEXT_USERNAME = "@Bik_Krlvn"
    }
}