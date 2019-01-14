package br.com.example.main.data.repository

import br.com.example.main.data.mapper.toBook
import br.com.example.main.data.service.BooksService
import br.com.example.main.viewmodel.model.Books

interface IBooksRepository {
    suspend fun getBooks(): MutableList<Books>
}

class BooksRepository constructor(
    private val service: BooksService
) : IBooksRepository {

    override suspend fun getBooks(): MutableList<Books> {
        return service.getBooks().await().toBook()
    }

}