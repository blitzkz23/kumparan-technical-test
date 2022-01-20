package com.naufaldystd.kumparanposting.ui.details.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufaldystd.kumparanposting.R
import com.naufaldystd.kumparanposting.data.source.remote.response.PhotoResponseItem
import com.naufaldystd.kumparanposting.databinding.ItemPhotoBinding
import com.squareup.picasso.Picasso

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
	private var listPhoto = ArrayList<PhotoResponseItem>()

	/**
	 * This function will be used to set the adapter's data from activity
	 */
	fun setPhoto(photo: List<PhotoResponseItem>?) {
		if (photo == null) return
		this.listPhoto.clear()
		this.listPhoto.addAll(photo)
	}

	class PhotoViewHolder(private val binding: ItemPhotoBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(photo: PhotoResponseItem) {
			binding.apply {
				Picasso.get().load(photo.thumbnailUrl).placeholder(R.drawable.ic_loading).into(photoThumbnail)
				photoTitle.text = photo.title
			}
		}
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): PhotoViewHolder {
		val itemPostBinding =
			ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return PhotoViewHolder(itemPostBinding)
	}

	override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
		val photo = listPhoto[position]
		holder.bind(photo)
	}

	override fun getItemCount(): Int = listPhoto.size
}