package jp.speakbuddy.edisonandroidexercise.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import jp.speakbuddy.edisonandroidexercise.common.Constants.FACT
import jp.speakbuddy.edisonandroidexercise.common.Constants.FACT_LEN
import jp.speakbuddy.edisonandroidexercise.data.remote.FactService
import jp.speakbuddy.edisonandroidexercise.data.remote.dtos.FactResponse
import jp.speakbuddy.edisonandroidexercise.domain.model.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class FactRepositoryImpl @Inject constructor(
    private val api: FactService,
    private val dataStore: DataStore<Preferences>
) : FactRepository {
    override suspend fun getFacts(): Flow<Fact> {
        return dataStore.data.catch {
            //Can Log exception here
        }.map { pref ->
            Fact(pref[KEY_FACT] ?: "", pref[KEY_FACT_LEN] ?: -1)
        }
    }

    override suspend fun fetchFact() {
        try {
            val response = api.getFact()
            updateDataStore(response)
        } catch (e: Exception) {
            Log.e(TAG, "fetchFact: $e")
        }
    }

    private suspend fun updateDataStore(response: FactResponse) {
        dataStore.edit { pref ->
            pref[KEY_FACT] = response.fact
            pref[KEY_FACT_LEN] = response.length
        }
    }

    private companion object {
        const val TAG = "FactRepository"
        val KEY_FACT = stringPreferencesKey(name = FACT)
        val KEY_FACT_LEN = intPreferencesKey(name = FACT_LEN)
    }
}