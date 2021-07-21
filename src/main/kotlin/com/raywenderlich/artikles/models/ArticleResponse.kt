package com.raywenderlich.artikles.models

import com.raywenderlich.artikles.entities.Article
import com.raywenderlich.artikles.entities.ArticleType
import java.time.LocalDateTime

data class ArticleResponse(
    val id: String,
    val title: String,
    val body: String,
    val articleType: ArticleType,
    val lastUpdatedOn: LocalDateTime,
    val createdOn: LocalDateTime
) {
    companion object {
        fun from(entity: Article): ArticleResponse {
            return ArticleResponse(
                id = entity.id!!,
                title = entity.title!!,
                body = entity.body!!,
                lastUpdatedOn = entity.lastUpdatedOn!!,
                createdOn = entity.createdOn!!,
                articleType = entity.articleType!!
            )
        }
    }
}