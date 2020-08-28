package com.test.digitalnomads.ui.main.mvp

import com.arellomobile.mvp.MvpView
import com.test.digitalnomads.entities.News

interface MainMvpView: MvpView {

    fun showItems(news: News)
}