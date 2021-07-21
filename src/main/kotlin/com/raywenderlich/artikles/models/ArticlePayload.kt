package com.raywenderlich.artikles.models

import com.raywenderlich.artikles.entities.ArticleType

data class ArticlePayload(
    val title: String?,
    val body: String?,
    val articleType: ArticleType?,
)