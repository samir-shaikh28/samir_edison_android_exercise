package jp.speakbuddy.edisonandroidexercise.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.MainDispatcherRule
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.domain.usecase.GetFactUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetFactUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repositoryMock: FactRepository = mockk()

    lateinit var getFactUseCase: GetFactUseCase


    @Before
    fun setup() {
        getFactUseCase = GetFactUseCase(
            repository = repositoryMock
        )
    }

    @Test
    fun onInvoke_hitGetFactsApi() = runTest {
        //Setup
        coEvery { repositoryMock.getFacts() } returns flow {  }
        //Action
        getFactUseCase()
        //Assert
        coVerify(exactly = 1) { repositoryMock.getFacts() }
    }

}