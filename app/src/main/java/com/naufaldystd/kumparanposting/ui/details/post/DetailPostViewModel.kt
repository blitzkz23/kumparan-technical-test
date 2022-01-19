package com.naufaldystd.kumparanposting.ui.details.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.naufaldystd.kumparanposting.data.Repository
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem

class DetailPostViewModel(private val mRepository: Repository) : ViewModel() {
	private var postId: Int = 0

	fun setSelectedPost(postId: Int) {
		this.postId = postId
	}

	fun getPostById(): LiveData<PostResponseItem> = mRepository.getPostById(postId)
}