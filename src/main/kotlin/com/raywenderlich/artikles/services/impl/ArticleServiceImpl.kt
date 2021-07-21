package com.raywenderlich.artikles.services.impl

import com.raywenderlich.artikles.NotFoundException
import com.raywenderlich.artikles.entities.Article
import com.raywenderlich.artikles.entities.ArticleType
import com.raywenderlich.artikles.repositories.ArticleRepository
import com.raywenderlich.artikles.services.ArticleService
import org.springframework.stereotype.Service

@Service
class ArticleServiceImpl(private val repository: ArticleRepository) : ArticleService {

    override fun getAllArticles(): List<Article> = repository.findAll()

    override fun createOrUpdateArticle(article: Article): Article {
        val entity = article.id?.let(::getArticleById) ?: article
        article.body?.let { entity.body = it }
        article.title?.let { entity.title = it }
        article.articleType?.let { entity.articleType = it }

        if (entity.articleType == null) {
            entity.articleType = ArticleType.FREE
        }

        return repository.save(entity)
    }

    @Throws(NotFoundException::class)
    override fun getArticleById(id: String): Article {
        return repository.findArticleById(id) ?: throw NotFoundException(entityName = "article")
    }

    override fun deleteArticleById(id: String) {
        val entity = getArticleById(id)
        repository.deleteById(entity.id!!)
    }

}