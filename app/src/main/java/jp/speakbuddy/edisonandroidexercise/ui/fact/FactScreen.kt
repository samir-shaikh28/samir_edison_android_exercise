package jp.speakbuddy.edisonandroidexercise.ui.fact

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.domain.model.Fact
import jp.speakbuddy.edisonandroidexercise.domain.model.getFormattedFact
import jp.speakbuddy.edisonandroidexercise.ui.component.LoaderUi
import jp.speakbuddy.edisonandroidexercise.ui.component.NoInternetConnectionUi
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactScreen(
    factScreenUiState: FactScreenUiState = FactScreenUiState(),
    snackBarEvent: SnackBarEvent = SnackBarEvent(),
    updateFacts: () -> Unit,
    onShareClick: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val fact = factScreenUiState.fact

    LaunchedEffect(key1 = snackBarEvent) {
        if (snackBarEvent.message.isNotBlank()) {
            snackBarHostState.currentSnackbarData?.dismiss()
            snackBarHostState.showSnackbar(snackBarEvent.message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.facts),
                        modifier = Modifier.fillMaxWidth(1f),
                        style = TextStyle(fontSize = 22.sp)
                    )
                },
                actions = {
                    // show share icon only when fact is available
                    if (fact != null && fact.fact.isNotBlank()) {
                        IconButton(
                            modifier = Modifier.testTag("ShareMenuButton"),
                            onClick = onShareClick
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = stringResource(id = R.string.share)
                            )
                        }
                    }
                })
        },
        bottomBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    enabled = !factScreenUiState.isLoading,
                    onClick = {
                        updateFacts.invoke()
                    }) {
                    if (factScreenUiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(12.dp))
                    } else {
                        Text(text = "Update Fact")
                    }
                }
            }
        },
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (factScreenUiState.isNetworkAvailable && (fact == null || fact.fact.isBlank())) {
                LoaderUi()
            } else if (!factScreenUiState.isNetworkAvailable && fact?.fact?.isBlank() == true) {
                NoInternetConnectionUi()
            } else {
                Column(
                    modifier = Modifier
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        )
                        .verticalScroll(rememberScrollState())
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (fact?.showMultipleCatsLabel == true) {
                        Text(
                            modifier = Modifier.testTag("MultipleCats"),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            text = stringResource(id = R.string.multiple_cats)
                        )
                    }
                    Text(
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center
                        ),
                        text = fact?.fact?.getFormattedFact() ?: ""
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (fact?.showLengthLabel == true) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("FactLength"),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.labelSmall,
                            text = "Length: ${fact.length}"
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(
            FactScreenUiState(
                isLoading = false,
                Fact(
                    "Amazing fact about Goku. He Doesn't Know His Own Limits.",
                    56
                ),
                isNetworkAvailable = true,
            ),
            snackBarEvent = SnackBarEvent(),
            updateFacts = {},
            onShareClick = {}
        )
    }
}