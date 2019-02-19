package br.com.example.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.example.main.data.repository.IBooksRepository

class BooksViewModel constructor(
    private val repository: IBooksRepository
) : ViewModel(), ViewModelProvider.Factory {

    val showLoading = MutableLiveData<Boolean>()
    val showBooks = Transformations.map(repository.getBooksResult) { it.toMutableList() }

    fun getBooks() {
        repository.getBooks()
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BooksViewModel(repository) as T
    }
}