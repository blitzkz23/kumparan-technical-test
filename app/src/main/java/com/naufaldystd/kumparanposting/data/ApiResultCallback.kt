package com.naufaldystd.kumparanposting.data

interface ApiResultCallback<T> {
	fun onPostsDataReceived(responses: T)
	fun onDataNotAvailable(e: Exception)
}