package jp.speakbuddy.edisonandroidexercise

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import jp.speakbuddy.edisonandroidexercise.domain.model.Fact
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreen
import jp.speakbuddy.edisonandroidexercise.ui.fact.SnackBarEvent
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.ui.viewmodels.FactViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val factViewModel: FactViewModel by viewModels<FactViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContent {
            EdisonAndroidExerciseTheme {
                val factScreenUiState by factViewModel.uiState.collectAsStateWithLifecycle()
                val snackBarEvent by factViewModel.snackBarEvent.collectAsStateWithLifecycle(SnackBarEvent())

                FactScreen(
                    factScreenUiState = factScreenUiState,
                    snackBarEvent = snackBarEvent,
                    updateFacts =  factViewModel::fetchFacts,
                    onShareClick = { factScreenUiState.fact?.let { shareFact(it) } }
                )
            }
        }
    }

    private fun shareFact(fact: Fact) {
        val shareIntent = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, fact.fact)
            type = "text/plain"
        }, getString(R.string.facts))
        startActivity(shareIntent)
    }

    private fun init() {
        factViewModel.init()
    }
}