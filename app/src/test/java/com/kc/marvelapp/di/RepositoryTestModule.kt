package com.kc.marvelapp.di

import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.domain.repository.MarvelRepositoryFake
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object RepositoryTestModule {
    @Provides
    fun provideMarvelRepository(): MarvelRepository {
        return MarvelRepositoryFake()
    }
}