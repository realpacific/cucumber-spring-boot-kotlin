package com.raywenderlich.artikles.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty

@Entity
class Article {

    @field:Id
    @field:GeneratedValue(generator = "uuid2")
    @field:GenericGenerator(name = "uuid2", strategy = "uuid2")
    var id: String? = null

    @field:Column(nullable = false)
    @field:NotEmpty(message = "Title is empty")
    var title: String? = null

    @field:Column(nullable = false, length = 6000)
    @field:NotEmpty(message = "Body is empty")
    var body: String? = null

    @field:Column(nullable = false)
    var articleType: ArticleType? = ArticleType.FREE

    @field:UpdateTimestamp
    var lastUpdatedOn: LocalDateTime? = null

    @field:CreationTimestamp
    var createdOn: LocalDateTime? = null
}