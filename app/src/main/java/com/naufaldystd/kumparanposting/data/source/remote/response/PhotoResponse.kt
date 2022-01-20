package com.naufaldystd.kumparanposting.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PhotoResponseItem(

	@field:SerializedName("albumId")
	val albumId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("thumbnailUrl")
	val thumbnailUrl: String
)
