package com.naufaldystd.kumparanposting.ui.details.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.naufaldystd.kumparanposting.data.Repository
import com.naufaldystd.kumparanposting.data.source.remote.response.PhotoResponseItem

class AlbumViewModel(private val mRepository: Repository) : ViewModel() {
	private var albumId = 0

	fun setSelectedAlbum(albumId: Int) {
		this.albumId = albumId
	}

	fun getPhotosByAlbum(): LiveData<List<PhotoResponseItem>> = mRepository.getPhotosByAlbum(albumId)
}