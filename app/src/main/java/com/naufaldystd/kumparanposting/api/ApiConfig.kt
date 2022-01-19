package com.naufaldystd.kumparanposting.api

import com.naufaldystd.kumparanposting.BuildConfig
import com.naufaldystd.kumparanposting.utils.Constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
	companion object {
		fun getApiService(): ApiService {
			val httpClient = OkHttpClient.Builder()
				.readTimeout(15, TimeUnit.SECONDS)
				.connectTimeout(15, TimeUnit.SECONDS)

			val loggingInterceptor =
				if (BuildConfig.DEBUG) {
					HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
				} else {
					HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
				}
			httpClient.addInterceptor(loggingInterceptor)

			val retrofit = Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(httpClient.build())
				.build()

			return retrofit.create(ApiService::class.java)
		}
	}
}