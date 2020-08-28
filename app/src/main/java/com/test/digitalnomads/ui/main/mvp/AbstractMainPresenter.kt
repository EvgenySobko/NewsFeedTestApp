package com.test.digitalnomads.ui.main.mvp

import android.content.Context
import com.arellomobile.mvp.MvpPresenter

interface MainPresenter {

    fun getNews(pageNum: Int)

    fun isInternetAvailable(context: Context): Boolean
}

abstract class AbstractMainPresenter: MvpPresenter<MainMvpView>(), MainPresenter