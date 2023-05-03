package com.kc.marvelapp.domain.models

data class AllCharactersResponse(
    val code: Int,
    var data: Data,
    val status: String
)