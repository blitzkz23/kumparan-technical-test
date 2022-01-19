package com.naufaldystd.kumparanposting.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.naufaldystd.kumparanposting.api.ApiConfig
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class RemoteDataSource {
	fun getAllPosts(callback: ApiResultCallback) {
		CoroutineScope(Dispatchers.IO).launch {
			val client = ApiConfig.getApiService().getPlaceholderData("posts")
			try {
				val response = client.awaitResponse()
				if(response.isSuccessful) {
					response.body()?.postResponse?.let {
						callback.onPostDataReceived(it)
					}
				} else {
					Log.d(TAG, "onResponse: ${response.message()}")
				}
			}catch(e: Exception) {
				callback.onDataNotAvailable()
			}
		}
	}

	interface ApiResultCallback {
		fun onPostDataReceived(responses: List<PostResponseItem>)
		fun onDataNotAvailable()
	}

	companion object {
		@Volatile
		private var instance: RemoteDataSource ?= null

		fun getInstance(): RemoteDataSource =
			instance ?: synchronized(this) {
				RemoteDataSource().apply { instance = this }
			}
	}
}