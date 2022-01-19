package com.naufaldystd.kumparanposting.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.naufaldystd.kumparanposting.api.ApiConfig
import com.naufaldystd.kumparanposting.data.ApiResultCallback
import com.naufaldystd.kumparanposting.data.source.remote.response.CommentResponseItem
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class RemoteDataSource {

	/**
	 * Fetch all post data from endpoint /posts and receive the responses in callback function.
	 */
	fun getAllPosts(callback: LoadPostsCallback) {
		CoroutineScope(Dispatchers.IO).launch {
			val client = ApiConfig.getApiService().getPlaceholderData("posts")
			try {
				val response = client.awaitResponse()
				if (response.isSuccessful) {
					response.body()?.let {
						callback.onDataReceived(it)
					}
				} else {
					Log.d(TAG, "onResponse: ${response.message()}")
				}
			} catch (e: Exception) {
				callback.onDataNotAvailable(e)
			}
		}
	}

	/**
	 * Fetch post data by post id from endpoints /posts/id and receive the responses in callback function.
	 */
	fun getPostById(id: Int, callback: LoadPostByIdCallback) {
		CoroutineScope(Dispatchers.IO).launch {
			val client = ApiConfig.getApiService().getDataById("posts", id)
			try {
				val response = client.awaitResponse()
				if (response.isSuccessful) {
					response.body()?.let {
						callback.onDataReceived(it)
					}
				} else {
					Log.d(TAG, "onResponse: ${response.message()}")
				}
			} catch (e: Exception) {
				callback.onDataNotAvailable(e)
			}
		}
	}

	/**
	 * Fetch comment data by post id from endpoints /posts/id/comments and receive the responses in callback function.
	 */
	fun getCommentsByPost(postId: Int, callback: LoadCommentByPostCallback) {
		CoroutineScope(Dispatchers.IO).launch {
			val client = ApiConfig.getApiService().getCommentByPost("posts", postId)
			try {
				val response = client.awaitResponse()
				if (response.isSuccessful) {
					response.body()?.let {
						callback.onDataReceived(it)
					}
				} else {
					Log.d(TAG, "onResponse: ${response.message()}")
				}
			} catch (e: Exception) {
				callback.onDataNotAvailable(e)
			}
		}
	}

	interface LoadPostsCallback : ApiResultCallback<List<PostResponseItem>>
	interface LoadPostByIdCallback : ApiResultCallback<PostResponseItem>
	interface LoadCommentByPostCallback : ApiResultCallback<List<CommentResponseItem>>

	companion object {
		@Volatile
		private var instance: RemoteDataSource? = null

		fun getInstance(): RemoteDataSource =
			instance ?: synchronized(this) {
				RemoteDataSource().apply { instance = this }
			}
	}
}