package com.naufaldystd.kumparanposting.data

interface ApiResultCallback<T> {
	fun onDataReceived(responses: T)
	fun onDataNotAvailable(e: Exception)
}