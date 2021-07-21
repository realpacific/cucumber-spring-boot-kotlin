package com.raywenderlich.artikles.models

import com.raywenderlich.artikles.entities.ArticleType
import java.time.LocalDateTime

data class ArticleResponse(
    val id: String,
    val title: String,
    val body: String,
    val articleType: ArticleType,
    val lastUpdatedOn: LocalDateTime,
    val createdOn: LocalDateTime
)