package jp.speakbuddy.edisonandroidexercise.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class FactResponse(
    val fact: String,
    val length: Int
)