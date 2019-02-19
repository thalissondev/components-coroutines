package br.com.example

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

class CustomApplication : Application() {
    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        SplitCompat.install(this)
    }
}