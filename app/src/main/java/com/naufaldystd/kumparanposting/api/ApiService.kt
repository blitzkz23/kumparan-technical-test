package com.naufaldystd.kumparanposting.api

import com.naufaldystd.kumparanposting.data.source.remote.response.CommentResponseItem
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
	@GET("{urlType}")
	fun getPlaceholderData(
		@Path("urlType") urlType: String
	): Call<List<PostResponseItem>>

	@GET("{urlType}/{id}")
	fun getDataById(
		@Path("urlType") urlType: String,
		@Path("id") id: Int
	): Call<PostResponseItem>

	@GET("{urlType}/{id}/comments")
	fun getCommentByPost(
		@Path("urlType") urlType: String,
		@Path("id") id: Int,
	): Call<List<CommentResponseItem>>
}