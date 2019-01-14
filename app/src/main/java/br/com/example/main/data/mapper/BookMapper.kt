package br.com.example.main.data.mapper

import br.com.example.main.data.model.BooksResponse
import br.com.example.main.viewmodel.model.Books

fun MutableList<BooksResponse>.toBook() = map {
    Books(it.url, it.name)
}.toMutableList()