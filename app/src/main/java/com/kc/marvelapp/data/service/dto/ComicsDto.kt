package com.kc.marvelapp.data.service.dto

import com.google.gson.annotations.SerializedName

data class ComicsDto(
    val available: Int,
    val collectionURI: String,
    @SerializedName("items") val itemDtos: List<ItemDto>,
    val returned: Int
)