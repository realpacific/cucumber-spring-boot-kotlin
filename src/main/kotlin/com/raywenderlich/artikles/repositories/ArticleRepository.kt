package com.raywenderlich.artikles.repositories

import com.raywenderlich.artikles.entities.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, String> {

    fun findArticleById(id: String): Article?
}