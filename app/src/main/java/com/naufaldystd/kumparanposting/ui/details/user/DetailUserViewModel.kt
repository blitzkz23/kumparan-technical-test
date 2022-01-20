package com.naufaldystd.kumparanposting.ui.details.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.naufaldystd.kumparanposting.data.Repository
import com.naufaldystd.kumparanposting.data.source.remote.response.AlbumResponseItem
import com.naufaldystd.kumparanposting.data.source.remote.response.UserResponseItem

class DetailUserViewModel(private val mRepository: Repository) : ViewModel() {
	private var userId = 0

	fun setSelectedUser(userId: Int) {
		this.userId = userId
	}

	fun getUserById(): LiveData<UserResponseItem> = mRepository.getUserById(userId)

	fun getAlbumsByUser(): LiveData<List<AlbumResponseItem>> = mRepository.getAlbumsByUser(userId)
}