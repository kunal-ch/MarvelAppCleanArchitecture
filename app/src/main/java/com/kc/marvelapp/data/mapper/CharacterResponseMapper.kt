package com.kc.marvelapp.data.mapper

import com.kc.marvelapp.data.service.dto.*
import com.kc.marvelapp.domain.models.*

fun AllCharactersResponseDto.toAllCharactersResponse() = AllCharactersResponse(
        code = code,
        data = getData(dataDto),
        status = status
    )

private fun getData(dataDto: DataDto) = Data(
        count = dataDto.count,
        limit = dataDto.limit,
        offset = dataDto.offset,
        total = dataDto.total,
        characters = getComicCharacters(dataDto.resultDtos),
    )

private fun getComicCharacters(dtoList : List<ResultDto>): List<ComicCharacter> {
    val comicCharList = ArrayList<ComicCharacter>()
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

private fun getComics(dto : ComicsDto) = Comics(
        available = dto.available,
        collectionURI = dto.collectionURI,
        returned = dto.returned,
        items = getItems(dto.itemDtos),
    )

private fun getItems(dto: List<ItemDto>) : List<Item> {
    val itemList = ArrayList<Item>()
    dto.map {
        itemList.add(getItem(it))
    }
    return itemList
}

private fun getItem(dto : ItemDto) = Item(name = dto.name, resourceURI = dto.resourceURI)

private fun getThumbnail(dto : ThumbnailDto) = Thumbnail(extension = dto.extension, path = dto.path)

