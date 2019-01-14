package br.com.example.main.data.service

import br.com.example.main.data.model.BooksResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface BooksService {

    @GET("books/")
    fun getBooks(): Deferred<MutableList<BooksResponse>>

}