package com.kc.marvelapp.domain.models

data class ComicCharacter(
    val id: Int,
    val modified: String,
    val description: String,
    val name: String,
    val resourceURI: String,
    val thumbnail: Thumbnail,
    val comics: Comics
) {
    data class Comics(
        val available: Int,
        val collectionURI: String,
        val items: List<Item>,
        val returned: Int
    ) {
        data class Item(
            val name: String,
            val resourceURI: String
        )
    }
    data class Thumbnail(
        val extension: String,
        val path: String
    )
}