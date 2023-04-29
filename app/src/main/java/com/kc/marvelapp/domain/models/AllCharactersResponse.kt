package com.kc.marvelapp.domain.models

data class AllCharactersResponse(
    val code: Int,
    var data: Data,
    val status: String
) {
    data class Data(
        val count: Int,
        val limit: Int,
        val offset: Int,
        var characters: List<ComicCharacter>,
        val total: Int
    )
}