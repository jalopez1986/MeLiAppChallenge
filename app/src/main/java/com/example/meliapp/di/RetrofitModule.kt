package com.example.meliapp.di
import com.example.meliapp.core.search.infrastructure.ItemsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.mercadolibre.com/"

@Module
@InstallIn(FragmentComponent::class)
class RetrofitModule {

    @Provides
    fun itemsAPI(retrofit: Retrofit): ItemsAPI = retrofit.create(ItemsAPI::class.java)

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}