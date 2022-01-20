package com.naufaldystd.kumparanposting.ui.details.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.naufaldystd.kumparanposting.data.Repository
import com.naufaldystd.kumparanposting.data.source.remote.response.PhotoResponseItem

class PhotoViewModel(private val mRepository: Repository) : ViewModel() {
	var photoId: Int = 0

	fun setSelectedPhoto(photoId: Int) {
		this.photoId = photoId
	}

	fun getPhotosById(): LiveData<PhotoResponseItem> = mRepository.getPhotosById(photoId)
}