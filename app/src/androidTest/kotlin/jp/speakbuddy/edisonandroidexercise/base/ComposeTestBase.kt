package jp.speakbuddy.edisonandroidexercise.base

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule

open class ComposeTestBase {
    @get:Rule val composeTestRule = createComposeRule()
}