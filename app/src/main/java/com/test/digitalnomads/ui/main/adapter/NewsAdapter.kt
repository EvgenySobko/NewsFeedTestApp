package com.test.digitalnomads.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.digitalnomads.R
import com.test.digitalnomads.entities.ListItem
import com.test.digitalnomads.entities.News
import kotlinx.android.synthetic.main.item_news_list.view.*
import java.text.SimpleDateFormat

abstract class NewsAdapter(
    private val itemClick: (item: ListItem.Article) -> Unit,
    private val footerClick: () -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val itemList = mutableListOf<ListItem>()

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position] is ListItem.Article) 0
        else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return if (viewType == 0) NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news_list, parent, false))
        else NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_footer, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        if (position == itemCount - 5) loadNextPage()
        if (position == itemCount - 1) {
            checkInternet()
        }
        if (itemList[position] is ListItem.Article) holder.bindArticle(itemList[position] as ListItem.Article)
        else holder.bindFooter()
    }

    abstract fun checkInternet()

    abstract fun loadNextPage()

    fun addItems(news: News) {
        itemList.addAll(news.articles)
        notifyDataSetChanged()
    }

    fun addItem(item: ListItem.Footer) {
        itemList.add(item)
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindArticle(item: ListItem.Article) {
            with(itemView) {
                clickable.setOnClickListener { itemClick.invoke(item) }
                title.text = item.title
                text.text = item.content
                date.text = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(item.publishedAt).toString() /* TODO() */
                Glide.with(this).load(item.urlToImage).into(image)
            }
        }

        fun bindFooter() {
            with(itemView) {
                clickable.setOnClickListener {
                    itemList.remove(ListItem.Footer)
                    footerClick.invoke()
                }
            }
        }
    }
}