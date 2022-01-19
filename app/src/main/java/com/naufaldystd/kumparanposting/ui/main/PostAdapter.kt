package com.naufaldystd.kumparanposting.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufaldystd.kumparanposting.R
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import com.naufaldystd.kumparanposting.databinding.ItemPostBinding

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
	private var listPost = ArrayList<PostResponseItem>()
	private lateinit var context: Context

	fun setPost(posts: List<PostResponseItem>?) {
		if (posts == null) return
		this.listPost.clear()
		this.listPost.addAll(posts)
	}

	class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(post: PostResponseItem) {
			with(binding) {
				postTitle.text = post.title
				val userId = post.userId.toString()
				postUser.text = itemView.context.getString(R.string.by_user, userId)
				postMessage.text = post.body

				itemView.setOnClickListener {

				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
		val itemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return PostViewHolder(itemPostBinding)
	}

	override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
		context = holder.itemView.context
		val post = listPost[position]
		holder.bind(post)
	}

	override fun getItemCount(): Int = listPost.size
}