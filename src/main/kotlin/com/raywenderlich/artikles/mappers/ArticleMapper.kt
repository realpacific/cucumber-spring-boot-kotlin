package com.raywenderlich.artikles.mappers

import com.raywenderlich.artikles.entities.Article
import com.raywenderlich.artikles.models.ArticlePayload
import com.raywenderlich.artikles.models.ArticleResponse

object ArticleMapper {
    fun fromRequest(payload: ArticlePayload): Article {
        val article = Article()
        return with(article) {
            title = payload.title
            body = payload.body
            articleType = payload.articleType
            this
        }
    }

    fun toResponse(entity: Article): ArticleResponse {
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