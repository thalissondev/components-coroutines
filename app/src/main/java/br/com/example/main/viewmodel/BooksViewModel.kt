package br.com.example.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.example.main.data.repository.IBooksRepository
import br.com.example.main.viewmodel.model.Books
import br.com.example.utils.Coroutines

class BooksViewModel constructor(
    private val repository: IBooksRepository
) : ViewModel(), ViewModelProvider.Factory {

    val showLoading = MutableLiveData<Boolean>()
    val showBooks = MutableLiveData<MutableList<Books>>()

    fun getBooks() {
        Coroutines.ioThenMain({
            showLoading.postValue(true)
            repository.getBooks()
        }, {
            showBooks.postValue(it)
            showLoading.postValue(false)
        })
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BooksViewModel(repository) as T
    }
}