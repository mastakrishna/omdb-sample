package com.kajdasz.sample.omdb.di

import com.kajdasz.sample.omdb.network.OmdbApiService
import com.kajdasz.sample.omdb.repository.SearchRepository
import com.kajdasz.sample.omdb.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideSearchRepository(
        omdbApiService: OmdbApiService,
    ): SearchRepository {
        return SearchRepositoryImpl(omdbApiService)
    }
}

