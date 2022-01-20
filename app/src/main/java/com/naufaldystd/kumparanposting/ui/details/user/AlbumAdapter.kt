package com.naufaldystd.kumparanposting.ui.details.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufaldystd.kumparanposting.data.source.remote.response.AlbumResponseItem
import com.naufaldystd.kumparanposting.databinding.ItemAlbumBinding

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
