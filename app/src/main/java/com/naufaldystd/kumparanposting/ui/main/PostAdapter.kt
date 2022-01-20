package com.naufaldystd.kumparanposting.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufaldystd.kumparanposting.R
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import com.naufaldystd.kumparanposting.databinding.ItemPostBinding
import com.naufaldystd.kumparanposting.ui.details.post.DetailPostActivity

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
	private var listPost = ArrayList<PostResponseItem>()
	private lateinit var context: Context

	/**
	 * This function will be used to set the adapter's data from activity
	 */
	fun setPost(posts: List<PostResponseItem>?) {
		if (posts == null) return
		this.listPost.clear()
		this.listPost.addAll(posts)
	}

	class PostViewHolder(private val binding: ItemPostBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(post: PostResponseItem) {
			with(binding) {
				postTitle.text = post.title
				val userId = post.userId.toString()
				postUser.text = itemView.context.getString(R.string.by_user, userId)
				postBody.text = post.body

				itemView.setOnClickListener {
					val intent = Intent(itemView.context, DetailPostActivity::class.java)
					intent.putExtra(DetailPostActivity.EXTRA_POST, post.id)
					itemView.context.startActivity(intent)
				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
		val itemPostBinding =
			ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return PostViewHolder(itemPostBinding)
	}

	override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
		context = holder.itemView.context
		val post = listPost[position]
		holder.bind(post)
	}

	override fun getItemCount(): Int = listPost.size
}