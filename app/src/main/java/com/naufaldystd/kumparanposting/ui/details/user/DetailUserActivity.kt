package com.naufaldystd.kumparanposting.ui.details.user

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufaldystd.kumparanposting.data.source.remote.response.UserResponseItem
import com.naufaldystd.kumparanposting.databinding.ActivityDetailUserBinding
import com.naufaldystd.kumparanposting.ui.ViewModelFactory

class DetailUserActivity : AppCompatActivity() {
	///Binding initialization using lazy
	private val activityDetailUserBinding: ActivityDetailUserBinding by lazy {
		ActivityDetailUserBinding.inflate(
			layoutInflater
		)
	}

	private var userId: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(activityDetailUserBinding.root)

		userId = intent.getIntExtra(EXTRA_USER, 0)

		///Set back button in toolbar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		val viewModel = obtainViewModel()
		viewModel.setSelectedUser(userId)

		///Observe user data and populate the views.
		activityDetailUserBinding.progressBar3.visibility = View.VISIBLE
		viewModel.getUserById().observe(this, { userDetail ->
			activityDetailUserBinding.progressBar3.visibility = View.GONE
			populateUserDetail(userDetail)
			supportActionBar?.title = "${userDetail.name}'s Profile"
		})

		///Observe albums data and show it in recyclerview.
		val albumAdapter = AlbumAdapter()
		activityDetailUserBinding.progressBar4.visibility = View.VISIBLE
		viewModel.getAlbumsByUser().observe(this, { albums ->
			activityDetailUserBinding.progressBar4.visibility = View.GONE
			albumAdapter.setAlbum(albums)
			albumAdapter.notifyDataSetChanged()
		})

		///RecyclerView setup
		activityDetailUserBinding.rvAlbums.apply {
			layoutManager = LinearLayoutManager(context)
			setHasFixedSize(true)
			isNestedScrollingEnabled = false
			adapter = albumAdapter
		}
	}

	/**
	 * This function is used to populate the view of detail with the data from API call.
	 */
	private fun populateUserDetail(userDetail: UserResponseItem?) {
		with(activityDetailUserBinding) {
			userDetail?.apply {
				userName.text = userDetail.name
				Log.d(TAG, "COK ${userDetail.name}")
				userUsername.text = userDetail.username
				userEmail.text = userDetail.email
				userAddress.text = "${userDetail.address.street}, ${userDetail.address.city}"
				userCompany.text = userDetail.company.name
			}
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
	private fun obtainViewModel(): DetailUserViewModel {
		val factory = ViewModelFactory.getInstance(this)
		return ViewModelProvider(this, factory)[DetailUserViewModel::class.java]
	}

	companion object {
		const val EXTRA_USER = "extra_user"
	}
}