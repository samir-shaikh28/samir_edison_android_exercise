package jp.speakbuddy.edisonandroidexercise.ui

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.MainDispatcherRule
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.domain.usecase.GetFactUseCase
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreenUiState
import jp.speakbuddy.edisonandroidexercise.ui.viewmodels.FactViewModel
import jp.speakbuddy.edisonandroidexercise.util.NetworkManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FactViewModelTest {

    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private var getFactUseCaseMock: GetFactUseCase = mockk()
    private var repositoryMock: FactRepository = mockk()
    private var networkManagerMock: NetworkManager = mockk()

    private lateinit var SUT: FactViewModel

    @Before
    fun setup() {
        SUT = FactViewModel(
            getFactUseCase = getFactUseCaseMock,
            repository = repositoryMock,
            networkManager = networkManagerMock,
            ioDispatchers = testDispatcher

        )
    }

    @Test
    fun testForFetchFact_MakeRepositoryCallWhenFetchFactCall() {
        // setup
        every { networkManagerMock.isNetworkAvailable() } returns true
        coEvery { repositoryMock.fetchFact() } just Runs
        // Action
        runTest {
            SUT.fetchFacts()
        }

        // Assert
        coVerify(exactly = 1) { repositoryMock.fetchFact() }
    }

    @Test
    fun testThat_ForNoInternetCorrectStateEmits() = runTest {
        // setup
        val expected = FactScreenUiState(isLoading = false, fact = null, isNetworkAvailable = false)
        every { networkManagerMock.isNetworkAvailable() } returns false
        coEvery { repositoryMock.fetchFact() } just Runs
        val values = mutableListOf<FactScreenUiState>()

        // Action
        SUT.fetchFacts()
        SUT.uiState.take(1).collectLatest {
            values.add(it)
        }

        //Assert
        assertTrue(values.last() == expected)
    }

}