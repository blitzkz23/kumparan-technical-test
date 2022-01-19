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
					response.body()?.let {
						callback.onPostDataReceived(it)
					}
				} else {
					Log.d(TAG, "onResponse: ${response.message()}")
				}
			}catch(e: Exception) {
				callback.onDataNotAvailable(e)
			}
		}
	}

	interface ApiResultCallback {
		fun onPostDataReceived(responses: List<PostResponseItem>)
		fun onDataNotAvailable(e: Exception)
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