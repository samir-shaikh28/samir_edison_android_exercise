package jp.speakbuddy.edisonandroidexercise.ui.fact

data class SnackBarEvent(
    val uid: Long = System.currentTimeMillis(),
    val message: String = "",
)