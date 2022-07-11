package com.anonlatte.natgeo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.databinding.ItemArticleBinding

class ArticlesAdapter(
    val onClickRead: (Article) -> Unit
) : ListAdapter<Article, ArticlesAdapter.ArticlesViewHolder>(
    DiffUtilCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArticlesViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.ivBackground.load(item.urlToImage)
            binding.tvTitle.text = item.title
            binding.btnRead.setOnClickListener {
                onClickRead(item)
            }
        }
    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }
}