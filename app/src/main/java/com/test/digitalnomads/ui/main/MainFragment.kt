package com.test.digitalnomads.ui.main

import android.os.Handler
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
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.ext.android.get

class MainFragment : BaseFragment(R.layout.main_fragment), MainMvpView {

    @InjectPresenter
    lateinit var presenter: AbstractMainPresenter

    @ProvidePresenter
    fun provide(): AbstractMainPresenter = get()

    private var pageNum = 2
    private var ifContainsFooter = false

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
                ifContainsFooter = false
            }
        }

        override fun checkInternet() {
            if (!presenter.isInternetAvailable(requireContext()) && !ifContainsFooter) {
                Handler().post { addItem(ListItem.Footer) }
                ifContainsFooter = true
            }
        }
    }

    override fun onInitView() {
        recyclerView.adapter = newsAdapter
        presenter.getNews(pageNum - 1)
    }

    override fun showItems(news: News) {
        with(newsAdapter) {
            addItems(news)
        }
    }
}