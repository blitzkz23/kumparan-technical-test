package com.naufaldystd.kumparanposting.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naufaldystd.kumparanposting.data.source.remote.RemoteDataSource
import com.naufaldystd.kumparanposting.data.source.remote.response.AlbumResponseItem
import com.naufaldystd.kumparanposting.data.source.remote.response.CommentResponseItem
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import com.naufaldystd.kumparanposting.data.source.remote.response.UserResponseItem

class Repository private constructor(private val remoteDataSource: RemoteDataSource) : DataSource {

	/**
	 * Implement the interface function of data source to get all posts and post the value as a live data.
	 */
	override fun getAllPosts(): LiveData<List<PostResponseItem>> {
		val placeholderPosts = MutableLiveData<List<PostResponseItem>>()
		remoteDataSource.getAllPosts(object : RemoteDataSource.LoadPostsCallback {
			override fun onDataReceived(responses: List<PostResponseItem>) {
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
				placeholderPosts.postValue(postsList)
			}

			override fun onDataNotAvailable(e: Exception) {
				Log.e(TAG, "onFailure: Posts failed to load. $e")
			}

		})
		return placeholderPosts
	}

	/**
	 * Implement the interface function of data source to get post by id and post the value as a live data.
	 */
	override fun getPostById(Id: Int): LiveData<PostResponseItem> {
		val placeholderPost = MutableLiveData<PostResponseItem>()
		remoteDataSource.getPostById(Id, object : RemoteDataSource.LoadPostByIdCallback {
			override fun onDataReceived(responses: PostResponseItem) {
				val postResult = PostResponseItem(
					responses.id,
					responses.title,
					responses.body,
					responses.userId
				)
				placeholderPost.postValue(postResult)
			}

			override fun onDataNotAvailable(e: Exception) {
				Log.e(TAG, "onFailure: Post failed to load. $e")
			}

		})
		return placeholderPost
	}

	/**
	 * Implement the interface function of data source to get comment data by post id and post the value as a live data.
	 */
	override fun getCommentsByPost(postId: Int): LiveData<List<CommentResponseItem>> {
		val placeholderComments = MutableLiveData<List<CommentResponseItem>>()
		remoteDataSource.getCommentsByPost(
			postId,
			object : RemoteDataSource.LoadCommentByPostCallback {
				override fun onDataReceived(responses: List<CommentResponseItem>) {
					val commentList = ArrayList<CommentResponseItem>()
					for (response in responses) {
						val comment = CommentResponseItem(
							response.name,
							response.postId,
							response.id,
							response.body,
							response.email
						)
						commentList.add(comment)
					}
					placeholderComments.postValue(commentList)
				}

				override fun onDataNotAvailable(e: Exception) {
					Log.e(TAG, "onFailure: Comments failed to load. $e")
				}

			})
		return placeholderComments
	}

	/**
	 * Implement the interface function of data source to get user data by id and post the value as a live data.
	 */
	override fun getUserById(Id: Int): LiveData<UserResponseItem> {
		val placeholderUser = MutableLiveData<UserResponseItem>()
		remoteDataSource.getUserById(Id, object : RemoteDataSource.LoadUserByIdCallback {
			override fun onDataReceived(responses: UserResponseItem) {
				val userResult = UserResponseItem(
					responses.address,
					responses.phone,
					responses.name,
					responses.company,
					responses.id,
					responses.email,
					responses.username
				)
				placeholderUser.postValue(userResult)
			}

			override fun onDataNotAvailable(e: Exception) {
				Log.e(TAG, "onFailure: Users failed to load. $e")
			}

		})
		return placeholderUser
	}

	/**
	 * Implement the interface function of data source to get albums data by user id and post the value as a live data.
	 */
	override fun getAlbumsByUser(Id: Int): LiveData<List<AlbumResponseItem>> {
		val placeholderAlbum = MutableLiveData<List<AlbumResponseItem>>()
		remoteDataSource.getAlbumsByUser(Id, object : RemoteDataSource.LoadAlbumsByUserCallback {
			override fun onDataReceived(responses: List<AlbumResponseItem>) {
				val albumList = ArrayList<AlbumResponseItem>()
				for (response in responses) {
					val album = AlbumResponseItem(response.id, response.title, response.userId)
					albumList.add(album)
				}
				placeholderAlbum.postValue(albumList)

			}

			override fun onDataNotAvailable(e: Exception) {
				Log.e(TAG, "onFailure: Albums failed to load. $e")
			}

		})
		return placeholderAlbum
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