package jp.speakbuddy.edisonandroidexercise.ui.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.di.IoDispatcher
import jp.speakbuddy.edisonandroidexercise.domain.usecase.GetFactUseCase
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreenUiState
import jp.speakbuddy.edisonandroidexercise.ui.fact.SnackBarEvent
import jp.speakbuddy.edisonandroidexercise.util.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FactViewModel @Inject constructor(
    private val getFactUseCase: GetFactUseCase,
    private val repository: FactRepository,
    private val networkManager: NetworkManager,
    @IoDispatcher private val ioDispatchers: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(FactScreenUiState(isLoading = true))
    val uiState = _state

    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent

    private var fetchFactJob: Job? = null

    fun init() {
        getFacts()
        fetchFacts()
    }

    private fun getFacts() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch(ioDispatchers) {
            getFactUseCase().collect { fact ->
                if (fact != null && fact.fact.isNotBlank()) {
                    _state.value = _state.value.copy(isLoading = false, fact = fact)
                } else {
                    if (isInternetAvailable()) {
                        emitSnackBarEvent("something went wrong!")
                    } else {
                        handleNoInternetConnection()
                    }
                }
            }
        }
    }

    fun fetchFacts() {
        if (!isInternetAvailable()) {
            handleNoInternetConnection()
            return
        }
        _state.value = _state.value.copy(isLoading = true)
        if (fetchFactJob?.isActive == true) fetchFactJob?.cancel()
        fetchFactJob = viewModelScope.launch(ioDispatchers) {
            repository.fetchFact()
        }
    }

    private fun isInternetAvailable() = networkManager.isNetworkAvailable()

    private fun handleNoInternetConnection() {
        _state.value = _state.value.copy(isNetworkAvailable = false, isLoading = false)
        emitSnackBarEvent("Network not available!")
    }

    private fun emitSnackBarEvent(message: String) {
        viewModelScope.launch {
            _snackBarEvent.emit(
                SnackBarEvent(message = message)
            )
        }
    }
}