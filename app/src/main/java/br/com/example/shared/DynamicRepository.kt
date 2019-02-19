package br.com.example.shared

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

interface IDynamicRepository {
    val onSuccess: MutableLiveData<Unit>
    val onError: MutableLiveData<Exception>
    fun getDynamicFeature(featureName: String)
}

class DynamicRepository constructor(
    private val context: Context
) : IDynamicRepository {

    private var sessionId = 0

    override val onSuccess: MutableLiveData<Unit> = MutableLiveData()
    override val onError: MutableLiveData<Exception> = MutableLiveData()

    override fun getDynamicFeature(featureName: String) {

        val splitManager = SplitInstallManagerFactory.create(context)
        val splitRequest = SplitInstallRequest
            .newBuilder()
            .addModule(featureName)
            .build()

        val splitListener = SplitInstallStateUpdatedListener { session ->
            if (session.sessionId() == sessionId) {
                if (session.status() == SplitInstallSessionStatus.INSTALLED) {
                    onSuccess.postValue(Unit) // Download completed
                }
            }
        }

        if (splitManager.installedModules.contains(featureName)) {
            onSuccess.postValue(Unit)
            return
        }

        splitManager.unregisterListener(splitListener)
        splitManager.startInstall(splitRequest)
            .addOnFailureListener { exception ->
                onError.postValue(exception) // Download failed
            }
            .addOnSuccessListener {
                sessionId = it
            }
    }
}