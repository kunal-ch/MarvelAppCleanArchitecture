package com.kc.marvelapp.data.mapper

import com.kc.marvelapp.data.service.dto.*
import com.kc.marvelapp.domain.models.AllCharactersResponse
import com.kc.marvelapp.domain.models.AllCharactersResponse.Data
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.models.ComicCharacter.*
import com.kc.marvelapp.domain.models.ComicCharacter.Comics.Item

fun AllCharactersResponseDto.toAllCharactersResponse(): AllCharactersResponse {
    return AllCharactersResponse(
        code = code,
        data = getData(dataDto),
        status = status
    )
}

fun getData(dataDto: DataDto) : Data {
    return Data(
        count = dataDto.count,
        limit = dataDto.limit,
        offset = dataDto.offset,
        total = dataDto.total,
        characters = getComicCharacters(dataDto.resultDtos),
    )
}

fun getComicCharacters(dtoList : List<ResultDto>): List<ComicCharacter> {
    var comicCharList = ArrayList<ComicCharacter>()
    dtoList.map {
        val comicCharacter = ComicCharacter(
            id = it.id,
            modified = it.modified,
            description = it.description,
            name = it.name,
            resourceURI = it.resourceURI,
            thumbnail = getThumbnail(it.thumbnailDto),
            comics = getComics(it.comicsDto)
        )
        comicCharList.add(comicCharacter)
    }
    return comicCharList
}

fun getComics(dto : ComicsDto) : Comics {
    return Comics(
        available = dto.available,
        collectionURI = dto.collectionURI,
        returned = dto.returned,
        items = getItems(dto.itemDtos),
    )
}

fun getItems(dto: List<ItemDto>) : List<Item> {
    var itemList = ArrayList<Item>()
    dto.map {
        itemList.add(getItem(it))
    }
    return itemList
}

fun getItem(dto : ItemDto) : Item {
    return Item(
        name = dto.name,
        resourceURI = dto.resourceURI,
    )
}

fun getThumbnail(dto : ThumbnailDto) : Thumbnail {
    return Thumbnail(
        extension = dto.extension,
        path = dto.path,
    )
}
