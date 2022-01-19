package com.naufaldystd.kumparanposting.api

import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponse
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
	@GET("{urlType}")
	fun getPlaceholderData(
		@Path("urlType") urlType: String
	): Call<PostResponse>

	@GET("{urlType}/{id}")
	fun getDataById(
		@Path("urlType") urlType: String,
		@Query("id") id: Int
	): Call<PostResponseItem>
}