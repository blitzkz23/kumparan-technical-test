package com.naufaldystd.kumparanposting.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.naufaldystd.kumparanposting.data.Repository
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem

class MainViewModel(private val mRepository: Repository) : ViewModel() {

	fun getAllPost(): LiveData<List<PostResponseItem>> = mRepository.getAllPosts()

}