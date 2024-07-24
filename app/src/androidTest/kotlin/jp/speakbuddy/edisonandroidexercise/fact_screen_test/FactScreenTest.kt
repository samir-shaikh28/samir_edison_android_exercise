package jp.speakbuddy.edisonandroidexercise.fact_screen_test

import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.speakbuddy.edisonandroidexercise.base.ComposeTestBase
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FactScreenTest : ComposeTestBase() {

    /**
     * Checks that the Progress Bar is shown when internet is available and fact is empty.
     */
    @Test
    fun whenInternetIsAvailableAndFactIsEmpty_showProgressBar() {
        composeTestRule.setContent {
            FactScreen(factScreenUiState = getFactScreenLoadingState(), updateFacts = { }, onShareClick = {})
        }
        composeTestRule.onNodeWithTag("ProgressBar").assertExists()
    }

    /**
     * Checks that the length label is shown when fact length is greater than 100.
     */
    @Test
    fun whenFactLengthIsGreaterThan100_showLengthLabel() {
        composeTestRule.setContent {
            FactScreen(factScreenUiState = getFactScreenStateWithFactOfLength120(), updateFacts = { }, onShareClick = {})
        }
        composeTestRule.onNodeWithTag("FactLength").assertExists()
    }

    /**
     * Checks that multiple cats label is shown when fact consist word cats.
     */
    @Test
    fun whenFactContainsWordCats_showMultipleCatsLabel() {
        composeTestRule.setContent {
            FactScreen(factScreenUiState = getFactScreeStateWithMultipleCats(), updateFacts = { }, onShareClick =
            {})
        }
        composeTestRule.onNodeWithTag("MultipleCats").assertExists()
    }

    /**
     * Checks that no internet connection composable is shown when internet is not available and fact is empty.
     */
    @Test
    fun whenInternetNotAvailable_showNoInternetConnectionComposable() {
        composeTestRule.setContent {
            FactScreen(factScreenUiState = getFactScreeStateForNoInternetAvailable(), updateFacts = { }, onShareClick =
            {})
        }
        composeTestRule.onNodeWithTag("NoInternetLabel").assertExists()
    }

    /**
     * Checks that share button is not shown when Fact is null.
     */
    @Test
    fun whenFactIsNull_doNotShowShareButton() {
        composeTestRule.setContent {
            FactScreen(factScreenUiState = getFactScreenLoadingState(), updateFacts = { }, onShareClick =
            {})
        }
        composeTestRule.onNodeWithTag("ShareMenuButton").assertDoesNotExist()
    }
}