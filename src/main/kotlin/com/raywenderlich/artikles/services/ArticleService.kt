package com.raywenderlich.artikles.services

import com.raywenderlich.artikles.entities.Article

interface ArticleService {

    fun getAllArticles(): List<Article>

    fun createOrUpdateArticle(article: Article): Article

    fun getArticleById(id: String): Article

    fun deleteArticleById(id: String)
}