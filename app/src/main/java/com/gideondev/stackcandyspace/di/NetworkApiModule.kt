package com.gideondev.stackcandyspace.di

import com.gideondev.stackcandyspace.Utils.Constants
import com.gideondev.stackcandyspace.dataApiCall.remote.ApiResponseCallAdapterFactory
import com.gideondev.stackcandyspace.dataApiCall.remote.StackExchangeCandySpaceApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * The Dagger Module to provide the instances of [OkHttpClient], [Retrofit], and [StackExchangeCandySpaceApiService] classes.
 * @author Gideon Oyediran
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkApiModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesStackExchangeApiService(retrofit: Retrofit): StackExchangeCandySpaceApiService {
        return retrofit.create(StackExchangeCandySpaceApiService::class.java)
    }
}