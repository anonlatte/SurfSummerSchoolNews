package com.anonlatte.natgeo.data.model.article

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val source: SourceArticle = SourceArticle(),
    val author: String? = null,
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String? = null,
    val publishedAt: String = "",
    val content: String? = null
) : Parcelable