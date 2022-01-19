package com.naufaldystd.kumparanposting.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naufaldystd.kumparanposting.data.source.remote.RemoteDataSource
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem

class Repository private constructor(private val remoteDataSource: RemoteDataSource) : DataSource {

	override fun getAllPosts(): LiveData<List<PostResponseItem>> {
		val placeHolderPosts = MutableLiveData<List<PostResponseItem>>()
		remoteDataSource.getAllPosts(object: RemoteDataSource.ApiResultCallback {
			override fun onPostDataReceived(responses: List<PostResponseItem>) {
				val postsList = ArrayList<PostResponseItem>()
				for (response in responses) {
					val post = PostResponseItem(
						response.id,
						response.title,
						response.body,
						response.userId
					)
					postsList.add(post)
				}
				placeHolderPosts.postValue(postsList)
			}

			override fun onDataNotAvailable(e: Exception) {
				Log.e(TAG, "onFailure: Posts failed to load $e")
			}

		})
		return placeHolderPosts
	}

	companion object {
		@Volatile
		private var instance: Repository? = null
		private const val TAG = "Repository"

		fun getInstance(remoteData: RemoteDataSource): Repository =
			instance ?: synchronized(this) {
				Repository(remoteData).apply { instance = this }
			}
	}
}