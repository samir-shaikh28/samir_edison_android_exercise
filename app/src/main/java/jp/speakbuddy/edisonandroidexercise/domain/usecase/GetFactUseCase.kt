package jp.speakbuddy.edisonandroidexercise.domain.usecase

import javax.inject.Inject
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.domain.model.Fact
import kotlinx.coroutines.flow.Flow

class GetFactUseCase @Inject constructor(
    private val repository: FactRepository,
) {
    suspend operator fun invoke(): Flow<Fact?> = repository.getFacts()
}