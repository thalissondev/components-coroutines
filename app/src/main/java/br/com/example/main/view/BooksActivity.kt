package br.com.example.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.example.R
import br.com.example.main.data.repository.BooksRepository
import br.com.example.main.data.repository.IBooksRepository
import br.com.example.main.data.service.BooksService
import br.com.example.main.viewmodel.BooksViewModel
import br.com.example.main.viewmodel.model.Books
import br.com.example.utils.Network
import br.com.example.utils.extensions.visible
import kotlinx.android.synthetic.main.activity_main.*

class BooksActivity : AppCompatActivity() {

    private lateinit var viewModel: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = Network.retrofit
            .create(BooksService::class.java)

        val repository: IBooksRepository =
            BooksRepository(service)

        viewModel = ViewModelProviders
            .of(this, BooksViewModel(repository))
            .get(BooksViewModel::class.java)

        bindObservers()
        viewModel.getBooks()
    }

    private fun bindObservers() {

        val loadingStatus = Observer<Boolean> {
            loading.visible(it)
        }

        val showBooks = Observer<MutableList<Books>> {
            result.text = it.toString()
        }

        viewModel.showLoading.observe(this, loadingStatus)
        viewModel.showBooks.observe(this, showBooks)

    }

}
