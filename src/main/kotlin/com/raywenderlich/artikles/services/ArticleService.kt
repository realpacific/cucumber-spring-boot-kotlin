package com.raywenderlich.artikles.services

import com.raywenderlich.artikles.NotFoundException
import com.raywenderlich.artikles.entities.Article
import com.raywenderlich.artikles.payloads.UpdateArticlePayload
import com.raywenderlich.artikles.repositories.ArticleRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class ArticleService(private val repository: ArticleRepository) {

    fun getAllArticles(): List<Article> = repository.findAll()

    fun createArticle(article: Article): Article {
        article.id = UUID.randomUUID().toString()
        article.createdOn = LocalDateTime.now()
        article.lastUpdatedOn = article.createdOn
        return repository.save(article)
    }

    fun updateArticle(id: String, payload: UpdateArticlePayload): Article {
        val article = getArticleById(id)
        if (!payload.requiresUpdate()) {
            return article
        }
        payload.body?.let { article.body = it }
        payload.title?.let { article.title = it }
        payload.articleType?.let { article.articleType = it }
        article.lastUpdatedOn = LocalDateTime.now()
        return repository.save(article)
    }

    @Throws(NotFoundException::class)
    fun getArticleById(id: String): Article {
        return repository.findArticleById(id) ?: throw NotFoundException("article")
    }

}