package com.raywenderlich.artikles.controllers

import com.raywenderlich.artikles.Resources
import com.raywenderlich.artikles.mappers.ArticleMapper
import com.raywenderlich.artikles.models.ArticlePayload
import com.raywenderlich.artikles.models.ArticleResponse
import com.raywenderlich.artikles.services.ArticleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/${Resources.ARTICLES}")
class ArticleController(private val service: ArticleService) {

    @GetMapping
    fun getAllArticles(): List<ArticleResponse> {
        return service.getAllArticles().map(ArticleResponse::from)
    }

    @GetMapping("/{id}")
    fun getArticleById(@PathVariable id: String): ArticleResponse {
        return service.getArticleById(id).let(ArticleResponse::from)
    }

    @PostMapping
    fun createArticle(@RequestBody payload: ArticlePayload): ArticleResponse {
        return service.createOrUpdateArticle(ArticleMapper.from(payload)).let(ArticleResponse::from)
    }

    @PutMapping("/{id}")
    fun updateArticle(@PathVariable id: String, @RequestBody payload: ArticlePayload): ArticleResponse {
        val article = ArticleMapper.from(payload)
        article.id = id
        return service.createOrUpdateArticle(article).let(ArticleResponse::from)
    }

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable id: String) {
        return service.deleteArticleById(id)
    }
}