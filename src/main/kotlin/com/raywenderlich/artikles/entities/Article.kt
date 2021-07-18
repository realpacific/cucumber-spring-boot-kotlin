package com.raywenderlich.artikles.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Article {

    @field:Id
    var id: String? = null

    @field:Column(nullable = false)
    var title: String? = null

    @field:Column(nullable = false, length = 6000)
    var body: String? = null

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = [CascadeType.ALL])
    var statistics: Statistics = Statistics()

    var articleType: ArticleType = ArticleType.FREE

    var lastUpdatedOn: LocalDateTime? = null

    var createdOn: LocalDateTime? = null
}