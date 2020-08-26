package com.test.digitalnomads.ui.main

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.test.digitalnomads.R
import com.test.digitalnomads.base.BaseFragment
import com.test.digitalnomads.entities.ListItem
import com.test.digitalnomads.entities.News
import com.test.digitalnomads.ui.main.adapter.NewsAdapter
import com.test.digitalnomads.ui.main.mvp.AbstractMainPresenter
import com.test.digitalnomads.ui.main.mvp.MainMvpView
import com.test.digitalnomads.ui.webview.WebViewFragment
import com.test.digitalnomads.utils.logd
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.ext.android.get

class MainFragment: BaseFragment(R.layout.main_fragment), MainMvpView {

    @InjectPresenter
    lateinit var presenter: AbstractMainPresenter

    @ProvidePresenter
    fun provide(): AbstractMainPresenter = get()

    private var pageNum = 2

    private val itemClick: (item: ListItem.Article) -> Unit = {
        fragmentManager!!.beginTransaction()
            .addToBackStack(null)
            .add(R.id.main, WebViewFragment.newInstance(it))
            .commit()
    }

    private val footerClick: () -> Unit = { presenter.getNews(pageNum) }

    private val newsAdapter = object : NewsAdapter(itemClick, footerClick) {
        override fun loadNextPage() {
            if (pageNum < 6 && presenter.isInternetAvailable(requireContext())) {
                presenter.getNews(pageNum)
                pageNum++
            }
        }

        override fun checkInternet() {
            if (!presenter.isInternetAvailable(requireContext())) presenter.addFooter()
        }
    }

    override fun onInitView() {
        recyclerView.adapter = newsAdapter
        presenter.getNews(1)
    }

    override fun showItems(news: News) {
        newsAdapter.addItems(news)
        logd(presenter.isInternetAvailable(requireContext()))
        if (!presenter.isInternetAvailable(requireContext())) newsAdapter.addItem(ListItem.Footer)
        else newsAdapter.removeFooter()
    }

    override fun showFooter() {
        newsAdapter.addItem(ListItem.Footer)
    }
}