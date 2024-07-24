package jp.speakbuddy.edisonandroidexercise.data.repository

import jp.speakbuddy.edisonandroidexercise.domain.model.Fact
import kotlinx.coroutines.flow.Flow

interface FactRepository {

    suspend fun fetchFact()
    suspend fun getFacts(): Flow<Fact?>
}