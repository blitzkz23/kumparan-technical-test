package com.naufaldystd.kumparanposting.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naufaldystd.kumparanposting.data.source.remote.RemoteDataSource
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem

class Repository private constructor(private val remoteDataSource: RemoteDataSource) : DataSource {

	/**
	 * Implement the interface function of remote data source to get all posts and post the value as a live data
	 */
	override fun getAllPosts(): LiveData<List<PostResponseItem>> {
		val placeHolderPosts = MutableLiveData<List<PostResponseItem>>()
		remoteDataSource.getAllPosts(object: RemoteDataSource.LoadPostsCallback {
			override fun onPostsDataReceived(responses: List<PostResponseItem>) {
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

	/**
	 * Implement the interface function of remote data source to get post by id and post the value as a live data
	 */
	override fun getPostById(Id: Int): LiveData<PostResponseItem> {
		val placeHolderPost = MutableLiveData<PostResponseItem>()
		remoteDataSource.getPostById(Id, object: RemoteDataSource.LoadPostByIdCallback {
			override fun onPostsDataReceived(responses: PostResponseItem) {
				val postResult = PostResponseItem(responses.id, responses.title, responses.body, responses.userId)
				placeHolderPost.postValue(postResult)
			}

			override fun onDataNotAvailable(e: Exception) {
				Log.e(TAG, "onFailure: TvShow failed to load.")
			}

		})
		return placeHolderPost
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