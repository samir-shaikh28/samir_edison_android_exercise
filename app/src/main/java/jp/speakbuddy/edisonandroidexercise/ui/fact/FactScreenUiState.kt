package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.runtime.Immutable
import jp.speakbuddy.edisonandroidexercise.domain.model.Fact

@Immutable
data class FactScreenUiState(
    val isLoading: Boolean = false,
    val fact: Fact? = null,
    val isNetworkAvailable: Boolean = true,
)