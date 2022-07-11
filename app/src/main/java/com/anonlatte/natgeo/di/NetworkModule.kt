package com.anonlatte.natgeo.di

import com.anonlatte.natgeo.BuildConfig
import com.anonlatte.natgeo.data.network.NatGeoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    fun provideOkhttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("X-Api-Key", BuildConfig.API_KEY)
                    .build()
                it.proceed(request)
            }
            .build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .build()
    }

    fun provideNatGeoApi(retrofit: Retrofit): NatGeoApi {
        return retrofit.create(NatGeoApi::class.java)
    }

    single { provideOkhttpClient() }
    single { provideRetrofit(get()) }
    single { provideNatGeoApi(get()) }
}