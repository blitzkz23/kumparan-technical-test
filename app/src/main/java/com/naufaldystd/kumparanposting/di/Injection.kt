package com.naufaldystd.kumparanposting.di

import android.content.Context
import com.naufaldystd.kumparanposting.data.Repository
import com.naufaldystd.kumparanposting.data.source.remote.RemoteDataSource

object Injection {
	fun provideRepository(context: Context): Repository {
		val remoteRepository = RemoteDataSource.getInstance()

		return Repository.getInstance(remoteRepository)
	}
}