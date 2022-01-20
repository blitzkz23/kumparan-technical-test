package com.naufaldystd.kumparanposting.ui.details.album

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.naufaldystd.kumparanposting.databinding.ActivityAlbumDetailBinding
import com.naufaldystd.kumparanposting.ui.ViewModelFactory

class AlbumDetailActivity : AppCompatActivity() {
	///Binding initialization using lazy
	private val activityAlbumDetailBinding: ActivityAlbumDetailBinding by lazy {
		ActivityAlbumDetailBinding.inflate(
			layoutInflater
		)
	}

	private var albumId: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(activityAlbumDetailBinding.root)

		albumId = intent.getIntExtra(EXTRA_ALBUM, 0)
		val albumName = intent.getStringExtra(EXTRA_ALBUM_NAME)

		///Set back button in toolbar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.title = "Album $albumName"

		val viewModel = obtainViewModel()
		viewModel.setSelectedAlbum(albumId)

		///Observe list of photo and show it in recyclerview.
		val photoAdapter = PhotoAdapter()
		activityAlbumDetailBinding.progressBar5.visibility = View.VISIBLE
		viewModel.getPhotosByAlbum().observe(this, { albums ->
			activityAlbumDetailBinding.progressBar5.visibility = View.GONE
			photoAdapter.setPhoto(albums)
			photoAdapter.notifyDataSetChanged()
		})

		//RecyclerView setup
		activityAlbumDetailBinding.rvPhoto.apply {
			layoutManager = GridLayoutManager(context, 2)
			setHasFixedSize(true)
			adapter = photoAdapter
		}

	}


	/**
	 * Override super to enable back button in the toolbar to be clicked.
	 */
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> super.onBackPressed()
		}
		return super.onOptionsItemSelected(item)
	}

	/**
	 * This function is used to obtain view model.
	 */
	private fun obtainViewModel(): AlbumViewModel {
		val factory = ViewModelFactory.getInstance(this)
		return ViewModelProvider(this, factory)[AlbumViewModel::class.java]
	}

	companion object {
		const val EXTRA_ALBUM = "extra_album"
		const val EXTRA_ALBUM_NAME = "extra_album_name"
	}
}