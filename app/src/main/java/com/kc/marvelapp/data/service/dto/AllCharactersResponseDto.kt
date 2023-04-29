package com.kc.marvelapp.data.service.dto

import com.google.gson.annotations.SerializedName

data class AllCharactersResponseDto(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    @SerializedName("data") val dataDto: DataDto,
    val etag: String,
    val status: String
)