package br.com.example.main.data.repository

import androidx.lifecycle.MutableLiveData
import br.com.example.main.data.mapper.toBook
import br.com.example.main.data.service.BooksService
import br.com.example.main.viewmodel.model.Books
import br.com.example.utils.Coroutines

interface IBooksRepository {
    val getBooksResult: MutableLiveData<MutableList<Books>>
    fun getBooks()
}

class BooksRepository constructor(
    private val service: BooksService
) : IBooksRepository {

    override val getBooksResult = MutableLiveData<MutableList<Books>>()

    override fun getBooks() {
        Coroutines.ioThenMain({
            service.getBooks().await().toBook()
        }, { books ->
            getBooksResult.postValue(books)
        })
    }

}