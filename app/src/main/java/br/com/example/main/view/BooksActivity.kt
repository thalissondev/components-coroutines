package br.com.example.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.example.R
import br.com.example.main.data.repository.BooksRepository
import br.com.example.main.data.repository.IBooksRepository
import br.com.example.main.data.service.BooksService
import br.com.example.main.viewmodel.BooksViewModel
import br.com.example.main.viewmodel.model.Books
import br.com.example.shared.DynamicModules
import br.com.example.shared.DynamicRepository
import br.com.example.shared.DynamicViewModel
import br.com.example.shared.IDynamicRepository
import br.com.example.utils.Network
import br.com.example.utils.extensions.visible
import kotlinx.android.synthetic.main.activity_main.*

class BooksActivity : AppCompatActivity() {

    private lateinit var booksViewModel: BooksViewModel
    private lateinit var dynamicViewModel: DynamicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = Network.retrofit
            .create(BooksService::class.java)

        // Settings books objects
        val booksRepository: IBooksRepository =
            BooksRepository(service)

        booksViewModel = ViewModelProviders
            .of(this, BooksViewModel(booksRepository))
            .get(BooksViewModel::class.java)

        // Settings dynamic objects
        val dynamicRepository: IDynamicRepository =
            DynamicRepository(this)

        dynamicViewModel = ViewModelProviders
            .of(this, DynamicViewModel(dynamicRepository))
            .get(DynamicViewModel::class.java)

        bindObservers()
        bindListeners()
        booksViewModel.getBooks()
    }

    private fun bindListeners() {
        download.setOnClickListener {
            dynamicViewModel.getDynamicFeature(DynamicModules.REGISTER)
        }

        start.setOnClickListener {
            openActivity()
        }
    }

    private fun openActivity() {
        val intent = Intent(
            this,
            Class.forName("br.com.example.register.RegisterActivity")
        )
        startActivity(intent)
    }

    private fun bindObservers() {

        val loadingStatus = Observer<Boolean> {
            loading.visible(it)
        }

        val showBooks = Observer<MutableList<Books>> {
            result.text = it.toString()
        }

        booksViewModel.showLoading.observe(this, loadingStatus)
        booksViewModel.showBooks.observe(this, showBooks)

        val successDynamic = Observer<Unit> {
            Toast.makeText(this, "Success download dynamic feature", Toast.LENGTH_LONG).show()
        }

        val errorDynamic = Observer<Exception> {
            Toast.makeText(this, "Exception: $it", Toast.LENGTH_LONG).show()
        }

        dynamicViewModel.onSuccess.observe(this, successDynamic)
        dynamicViewModel.onError.observe(this, errorDynamic)

    }

}
