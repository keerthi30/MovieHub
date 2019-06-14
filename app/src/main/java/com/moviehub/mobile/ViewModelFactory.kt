package com.moviehub.mobile

import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val creators: ArrayMap<Class<out ViewModel>, ViewModel>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: ViewModel? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class: $modelClass")
        }
        try {
            return creator as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}