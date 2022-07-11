package com.anonlatte.natgeo.data.model.article

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceArticle(
    val id: String? = null,
    val name: String = ""
) : Parcelable