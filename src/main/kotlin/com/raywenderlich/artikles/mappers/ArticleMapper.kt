package com.raywenderlich.artikles.mappers

import com.raywenderlich.artikles.entities.Article
import com.raywenderlich.artikles.models.ArticlePayload

object ArticleMapper {
    fun from(payload: ArticlePayload): Article {
        val article = Article()
        return with(article) {
            title = payload.title
            body = payload.body
            articleType = payload.articleType
            this
        }
    }
}