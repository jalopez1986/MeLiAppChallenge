package com.example.meliapp.di

import com.example.meliapp.core.search.domain.ItemsRepository
import com.example.meliapp.core.search.infrastructure.RetrofitItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindItemsRepository(retrofitItemsRepository: RetrofitItemsRepository) : ItemsRepository
}