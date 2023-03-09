package com.imams.simpleform.data.di.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.imams.simpleform.BuildConfig
import com.imams.simpleform.data.source.remote.ProvinceApi
import com.imams.simpleform.data.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun retrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BaseURLProvince)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        chuckInterceptor: ChuckerInterceptor,
    ) : OkHttpClient {
        val builder =  OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(chuckInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun createLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun createChuckInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideProvinceApiService(retrofit: Retrofit): ProvinceApi {
        return retrofit.create(ProvinceApi::class.java)
    }

}