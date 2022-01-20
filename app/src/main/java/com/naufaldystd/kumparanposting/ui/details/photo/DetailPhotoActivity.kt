package com.naufaldystd.kumparanposting.ui.details.photo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.naufaldystd.kumparanposting.R
import com.naufaldystd.kumparanposting.data.source.remote.response.PhotoResponseItem
import com.naufaldystd.kumparanposting.databinding.ActivityAlbumDetailBinding
import com.naufaldystd.kumparanposting.databinding.ActivityDetailPhotoBinding
import com.naufaldystd.kumparanposting.ui.ViewModelFactory
import com.naufaldystd.kumparanposting.ui.details.album.AlbumViewModel
import com.squareup.picasso.Picasso

class DetailPhotoActivity : AppCompatActivity() {
	///Binding initialization using lazy
	private val activityDetailPhotoBinding: ActivityDetailPhotoBinding by lazy {
		ActivityDetailPhotoBinding.inflate(
			layoutInflater
		)
	}

	private var photoId: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(activityDetailPhotoBinding.root)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		photoId = intent.getIntExtra(EXTRA_PHOTO, 0)

		val viewModel = obtainViewModel()
		viewModel.setSelectedPhoto(photoId)

		///Observe photo data and populate views
		viewModel.getPhotosById().observe(this, { photo ->
			populateDetailPhoto(photo)
		})
	}

	private fun populateDetailPhoto(photo: PhotoResponseItem?) {
		photo?.apply {
			with(activityDetailPhotoBinding) {
				Picasso.get().load(photo.url).placeholder(R.drawable.ic_loading)
					.error(R.drawable.ic_error).into(detailPhoto)
				photoTitle.text = photo.title
			}
			supportActionBar?.title = photo.title
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
	private fun obtainViewModel(): PhotoViewModel {
		val factory = ViewModelFactory.getInstance(this)
		return ViewModelProvider(this, factory)[PhotoViewModel::class.java]
	}

	companion object {
		const val EXTRA_PHOTO = "detail_photo"
	}
}