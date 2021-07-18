package com.raywenderlich.artikles.controllers

import com.raywenderlich.artikles.services.ArticleService
import com.raywenderlich.artikles.entities.Article
import com.raywenderlich.artikles.payloads.UpdateArticlePayload
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/articles")
class ArticleController(private val service: ArticleService) {

    @GetMapping
    fun getAllArticles(): List<Article> {
        return service.getAllArticles()
    }

    @GetMapping("/{id}")
    fun getArticleById(@PathVariable id: String): Article {
        return service.getArticleById(id)
    }

    @PostMapping
    fun createArticle(@RequestBody article: Article): Article {
        return service.createArticle(article)
    }

    @PutMapping("/{id}")
    fun updateArticle(@PathVariable id: String, @RequestBody payload: UpdateArticlePayload): Article {
        return service.updateArticle(id, payload)
    }
}