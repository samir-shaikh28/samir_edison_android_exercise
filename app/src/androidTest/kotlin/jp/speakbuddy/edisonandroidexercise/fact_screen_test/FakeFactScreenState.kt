package jp.speakbuddy.edisonandroidexercise.fact_screen_test

import jp.speakbuddy.edisonandroidexercise.domain.model.Fact
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreenUiState

fun getFactScreenLoadingState() = FactScreenUiState(isLoading = true, isNetworkAvailable = true)

fun getFactScreenStateWithFactOfLength120() =
    FactScreenUiState(
        isLoading = false, fact =
        Fact(
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                    " Lorem Ipsum has been the industry's standards",
            120
        )
    )

fun getFactScreeStateWithMultipleCats() = FactScreenUiState(isLoading = false, fact = Fact("cats have 9 lives", 20))

fun getFactScreeStateForNoInternetAvailable() = FactScreenUiState(
    isLoading = false, isNetworkAvailable = false, fact =
    Fact(
        "",
        20
    )
)