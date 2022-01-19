package com.naufaldystd.kumparanposting.ui.details.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.naufaldystd.kumparanposting.R
import com.naufaldystd.kumparanposting.data.source.remote.response.PostResponseItem
import com.naufaldystd.kumparanposting.databinding.ActivityDetailPostBinding
import com.naufaldystd.kumparanposting.ui.ViewModelFactory

class DetailPostActivity : AppCompatActivity() {
	private val activityDetailPostBinding: ActivityDetailPostBinding by lazy {
		ActivityDetailPostBinding.inflate(layoutInflater)
	}

	private var postId: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(activityDetailPostBinding.root)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		postId = intent.getIntExtra(EXTRA_POST, 0)

		val viewModel = obtainViewModel()
		viewModel.setSelectedPost(postId)
		activityDetailPostBinding.progressBar.visibility = View.VISIBLE

		///Observe detail post data and show it in RecyclerView.
		viewModel.getPostById().observe(this, { post ->
			activityDetailPostBinding.progressBar.visibility = View.GONE
			populatePostDetail(post)
		})
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