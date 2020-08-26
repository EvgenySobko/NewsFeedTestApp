package com.test.digitalnomads.ui.webview

import android.os.Bundle
import com.test.digitalnomads.R
import com.test.digitalnomads.base.BaseFragment
import com.test.digitalnomads.entities.ListItem
import kotlinx.android.synthetic.main.webview_fragment.*

class WebViewFragment: BaseFragment(R.layout.webview_fragment) {

    companion object {

        const val ARTICLE_URL = "article"

        @JvmStatic
        fun newInstance(article: ListItem.Article) = WebViewFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARTICLE_URL, article.url)
            }
        }
    }

    override fun onInitView() {
        webView.loadUrl(arguments!!.getString(ARTICLE_URL))
    }
}