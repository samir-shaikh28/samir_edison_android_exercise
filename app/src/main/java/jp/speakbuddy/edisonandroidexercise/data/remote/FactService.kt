package jp.speakbuddy.edisonandroidexercise.data.remote

import jp.speakbuddy.edisonandroidexercise.data.remote.dtos.FactResponse
import retrofit2.http.GET

interface FactService {
    @GET("fact")
    suspend fun getFact(): FactResponse
}