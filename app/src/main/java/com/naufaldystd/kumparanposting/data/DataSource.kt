package com.naufaldystd.kumparanposting.data

import androidx.lifecycle.LiveData
import com.naufaldystd.kumparanposting.data.source.remote.response.CommentResponseItem
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import com.naufaldystd.kumparanposting.data.source.remote.response.UserResponseItem

interface DataSource {
	fun getAllPosts(): LiveData<List<PostResponseItem>>
	fun getPostById(Id: Int): LiveData<PostResponseItem>
	fun getCommentsByPost(postId: Int): LiveData<List<CommentResponseItem>>
	fun getUserById(Id: Int): LiveData<UserResponseItem>
}