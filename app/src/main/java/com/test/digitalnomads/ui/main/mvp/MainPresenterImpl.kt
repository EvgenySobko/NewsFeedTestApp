package com.test.digitalnomads.ui.main.mvp

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.arellomobile.mvp.InjectViewState
import com.test.digitalnomads.api.NetworkRepository
import com.test.digitalnomads.api.Repository
import com.test.digitalnomads.utils.logd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


@InjectViewState
class MainPresenterImpl(
    private val repository: Repository,
    private val networkRepository: NetworkRepository
) : AbstractMainPresenter(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job()

    override fun getNews(pageNum: Int) {
        launch {
            runCatching { networkRepository.getNews(pageNum) }
                .onSuccess {
                    launch(Dispatchers.Main) { viewState.showItems(it!!) }
                    repository.saveNews(it!!)
                }
                .onFailure {
                    try {
                        runCatching { repository.getNews() }
                            .onSuccess { launch(Dispatchers.Main) { viewState.showItems(it) } }
                            .onFailure { logd("fail") }
                        logd("done")
                    } catch (e: Throwable) {
                        logd(e)
                    }
                }
        }
    }

    override fun isInternetAvailable(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    override fun addFooter() {
        viewState.showFooter()
    }
}