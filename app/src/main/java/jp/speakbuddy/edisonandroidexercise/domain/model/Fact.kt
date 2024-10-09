package jp.speakbuddy.edisonandroidexercise.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Fact(
    val fact: String,
    val length: Int
) {
    val showLengthLabel = length >= 100
    val showMultipleCatsLabel = fact.contains( "cats", ignoreCase = true) || fact.contains( "cat's", ignoreCase = true)
}

fun String.getFormattedFact() : String {
    return "\"$this\""
}