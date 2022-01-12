package com.gideondev.stackcandyspace.di

import android.app.Application
import com.gideondev.stackcandyspace.Utils.StringUtils
import com.gideondev.stackcandyspace.dataApiCall.remote.StackExchangeCandySpaceApiService
import com.gideondev.stackcandyspace.repository.StackExchangeCandySpaceRepository
import com.gideondev.stackcandyspace.repository.StackExchangeCandySpaceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository instances.
 * @author Gideon Oyediran
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideStringUtils(app: Application): StringUtils {
        return StringUtils(app)
    }

    @Singleton
    @Provides
    fun provideStackExchangeCandySpaceRepository(stringUtils: StringUtils, apiService: StackExchangeCandySpaceApiService): StackExchangeCandySpaceRepository {
        return StackExchangeCandySpaceRepositoryImpl(stringUtils, apiService)
    }
}
