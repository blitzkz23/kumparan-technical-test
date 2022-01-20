package com.naufaldystd.kumparanposting.ui.details.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufaldystd.kumparanposting.data.source.remote.response.CommentResponseItem
import com.naufaldystd.kumparanposting.databinding.ItemCommentBinding
import com.naufaldystd.kumparanposting.ui.details.post.CommentAdapter.CommentViewHolder

class CommentAdapter : RecyclerView.Adapter<CommentViewHolder>() {
	private var listComment = ArrayList<CommentResponseItem>()

	/**
	 * This function will be used to set the adapter's data from activity
	 */
	fun setComment(comment: List<CommentResponseItem>?) {
		if (comment == null) return
		this.listComment.clear()
		this.listComment.addAll(comment)
	}

	class CommentViewHolder(private val binding: ItemCommentBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(comment: CommentResponseItem) {
			with(binding) {
				commentUser.text = comment.name
				commentUserEmail.text = comment.email
				commentBody.text = comment.body
			}
		}
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): CommentViewHolder {
		val itemCommentBinding =
			ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return CommentViewHolder(itemCommentBinding)
	}

	override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
		val comment = listComment[position]
		holder.bind(comment)
	}

	override fun getItemCount(): Int = listComment.size
}