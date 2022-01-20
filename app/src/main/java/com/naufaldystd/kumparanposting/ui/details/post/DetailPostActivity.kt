package com.naufaldystd.kumparanposting.ui.details.post

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufaldystd.kumparanposting.R
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import com.naufaldystd.kumparanposting.databinding.ActivityDetailPostBinding
import com.naufaldystd.kumparanposting.ui.ViewModelFactory
import com.naufaldystd.kumparanposting.ui.details.user.DetailUserActivity

class DetailPostActivity : AppCompatActivity() {
	///Binding initalization using lazy
	private val activityDetailPostBinding: ActivityDetailPostBinding by lazy {
		ActivityDetailPostBinding.inflate(layoutInflater)
	}

	private var postId: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(activityDetailPostBinding.root)

		///Set back button in toolbar
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		postId = intent.getIntExtra(EXTRA_POST, 0)

		val viewModel = obtainViewModel()
		viewModel.setSelectedPost(postId)

		///Observe detail post data and populate the views.
		var userId = 0
		activityDetailPostBinding.progressBar.visibility = View.VISIBLE
		viewModel.getPostById().observe(this, { post ->
			activityDetailPostBinding.progressBar.visibility = View.GONE
			populatePostDetail(post)
			userId = post.userId
		})

		///Observe comment data and show it in RecyclerView.
		val commentAdapter = CommentAdapter()
		activityDetailPostBinding.progressBar2.visibility = View.VISIBLE
		viewModel.getCommentsByPost().observe(this, { comments ->
			activityDetailPostBinding.progressBar2.visibility = View.GONE
			commentAdapter.setComment(comments)
			commentAdapter.notifyDataSetChanged()
		})

		///RecyclerView setup
		activityDetailPostBinding.rvComment.apply {
			layoutManager = LinearLayoutManager(context)
			setHasFixedSize(true)
			adapter = commentAdapter
		}

		///Navigate to user detail page
		activityDetailPostBinding.postUser.setOnClickListener {
			val intent = Intent(this, DetailUserActivity::class.java)
			intent.putExtra(DetailUserActivity.EXTRA_USER, userId)
			startActivity(intent)
		}
	}

	/**
	 * This function is used to populate the view of detail with the data from API call.
	 */
	private fun populatePostDetail(postDetail: PostResponseItem?) {
		with(activityDetailPostBinding) {
			postDetail?.apply {
				postTitle.text = postDetail.title
				val userId = postDetail.userId.toString()
				postUser.text = getString(R.string.by_user, userId)
				postBody.text = postDetail.body
			}
		}
	}

	/**
	 * This function is used to obtain view model.
	 */
	private fun obtainViewModel(): DetailPostViewModel {
		val factory = ViewModelFactory.getInstance(this)
		return ViewModelProvider(this, factory)[DetailPostViewModel::class.java]
	}

	companion object {
		const val EXTRA_POST = "extra_post"
	}
}