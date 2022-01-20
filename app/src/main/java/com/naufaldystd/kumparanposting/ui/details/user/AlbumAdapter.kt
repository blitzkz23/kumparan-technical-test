package com.naufaldystd.kumparanposting.ui.details.user

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufaldystd.kumparanposting.data.source.remote.response.AlbumResponseItem
import com.naufaldystd.kumparanposting.databinding.ItemAlbumBinding
import com.naufaldystd.kumparanposting.ui.details.album.AlbumDetailActivity

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
	private val listAlbums = ArrayList<AlbumResponseItem>()

	/**
	 * This function will be used to set the adapter's data from activity
	 */
	fun setAlbum(album: List<AlbumResponseItem>?) {
		if (album == null) return
		this.listAlbums.clear()
		this.listAlbums.addAll(album)
	}

	class AlbumViewHolder(private val binding: ItemAlbumBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(album: AlbumResponseItem) {
			binding.albumUser.text = album.title
			itemView.setOnClickListener {
				val intent = Intent(itemView.context, AlbumDetailActivity::class.java)
				intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM, album.id)
				intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_NAME, album.title)
				itemView.context.startActivity(intent)
			}
		}
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): AlbumViewHolder {
		val itemAlbumBinding =
			ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return AlbumViewHolder(itemAlbumBinding)
	}

	override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
		val album = listAlbums[position]
		holder.bind(album)
	}

	override fun getItemCount(): Int = listAlbums.size

}
