package com.naufaldystd.kumparanposting.data

import androidx.lifecycle.LiveData
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponse
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem

interface DataSource {
	fun getAllPosts(): LiveData<List<PostResponseItem>>
}