package com.naufaldystd.kumparanposting.ui.main

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufaldystd.kumparanposting.R
import com.naufaldystd.kumparanposting.databinding.ActivityMainBinding
import com.naufaldystd.kumparanposting.ui.ViewModelFactory

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(activityMainBinding.root)
		actionBar?.title = getString(R.string.kumparan_posting)

		val viewModel = obtainViewModel()

		val postAdapter = PostAdapter()
		activityMainBinding.progressBar.visibility = View.VISIBLE

		///Observe posts data and show it in RecyclerView.
		viewModel.getAllPost().observe(this, { posts ->
			activityMainBinding.progressBar.visibility = View.GONE
			postAdapter.setPost(posts)
			postAdapter.notifyDataSetChanged()
		})

		activityMainBinding.rvPost.apply {
			layoutManager = LinearLayoutManager(context)
			setHasFixedSize(true)
			adapter = postAdapter
		}
	}

	/**
	 * This function is used to obtain view model.
	 */
	private fun obtainViewModel(): MainViewModel {
		val factory = ViewModelFactory.getInstance(this)
		return ViewModelProvider(this, factory)[MainViewModel::class.java]
	}
}