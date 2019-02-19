package br.com.example.shared

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DynamicViewModel constructor(
    private val repository: IDynamicRepository
) : ViewModel(), ViewModelProvider.Factory {

    val onSuccess = Transformations.map(repository.onSuccess) { Unit }
    val onError = Transformations.map(repository.onError) { it }

    fun getDynamicFeature(featureName: String) {
        repository.getDynamicFeature(featureName)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DynamicViewModel(repository) as T
    }
}