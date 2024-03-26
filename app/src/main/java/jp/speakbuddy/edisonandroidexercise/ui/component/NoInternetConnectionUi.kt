package jp.speakbuddy.edisonandroidexercise.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme

@Composable
fun NoInternetConnectionUi() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center).testTag("NoInternetLabel"),
            style = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            ),
            text = "Network Issue. Please Check Your Internet Connection!"
        )
    }
}

@Preview
@Composable
fun PreviewNoInternetConnection() {
    EdisonAndroidExerciseTheme {
        NoInternetConnectionUi()
    }
}