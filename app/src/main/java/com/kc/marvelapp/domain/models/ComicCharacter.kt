package com.kc.marvelapp.domain.models

data class ComicCharacter(
    val id: Int,
    val modified: String,
    val description: String,
    val name: String,
    val resourceURI: String,
    val thumbnail: Thumbnail,
    val comics: Comics
)