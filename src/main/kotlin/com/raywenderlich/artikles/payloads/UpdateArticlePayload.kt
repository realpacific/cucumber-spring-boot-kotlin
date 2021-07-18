package com.raywenderlich.artikles.payloads

import com.raywenderlich.artikles.entities.ArticleType

data class UpdateArticlePayload(
    val title: String?,
    val body: String?,
    val articleType: ArticleType?
) {
    fun requiresUpdate(): Boolean {
        return listOfNotNull(title, body, articleType?.name).isNotEmpty()
    }
}