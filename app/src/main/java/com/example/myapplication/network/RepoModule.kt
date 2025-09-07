package com.example.myapplication.network

import com.example.myapplication.data.ApiService
import com.example.myapplication.data.RepoImpl
import com.example.myapplication.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideRepository(api: ApiService): Repository =
        RepoImpl(api)
}