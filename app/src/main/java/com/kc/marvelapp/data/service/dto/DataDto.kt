package com.kc.marvelapp.data.service.dto

import com.google.gson.annotations.SerializedName

data class DataDto(
    val count: Int,
    val limit: Int,
    val offset: Int,
    @SerializedName("results") val resultDtos: List<ResultDto>,
    val total: Int
)