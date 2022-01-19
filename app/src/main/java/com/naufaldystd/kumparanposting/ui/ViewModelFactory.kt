package com.naufaldystd.kumparanposting.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naufaldystd.kumparanposting.data.Repository
import com.naufaldystd.kumparanposting.di.Injection
import com.naufaldystd.kumparanposting.ui.details.post.DetailPostViewModel
import com.naufaldystd.kumparanposting.ui.main.MainViewModel

class ViewModelFactory private constructor(private val mRepository: Repository) : ViewModelProvider.NewInstanceFactory() {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		return when {
			modelClass.isAssignableFrom(MainViewModel::class.java) -> {
				MainViewModel(mRepository) as T
			}
			modelClass.isAssignableFrom(DetailPostViewModel::class.java) -> {
				DetailPostViewModel(mRepository) as T
			}
			else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
		}
	}

	companion object {
		@Volatile
		private var instance: ViewModelFactory ?= null

		fun getInstance(context: Context): ViewModelFactory =
			instance ?: synchronized(this) {
				ViewModelFactory(Injection.provideRepository(context)).apply {
					instance = this
				}
			}
	}
}