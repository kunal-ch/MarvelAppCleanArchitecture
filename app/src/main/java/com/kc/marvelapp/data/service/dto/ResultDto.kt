package com.kc.marvelapp.data.service.dto

import com.google.gson.annotations.SerializedName

data class ResultDto(
    @SerializedName("comics") val comicsDto: ComicsDto,
    val description: String,
    @SerializedName("events") val eventsDto: EventsDto?,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    @SerializedName("series") val seriesDto: SeriesDto?,
    @SerializedName("stories") val storiesDto: StoriesDto?,
    @SerializedName("thumbnail") val thumbnailDto: ThumbnailDto,
    @SerializedName("urls") val urlDtos: List<UrlDto>?
)