package com.raywenderlich.artikles.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Statistics {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    var likes: Int = 0

    var shareCount: Int = 0

    var viewCount: Int = 0

    companion object {
        fun new() = Statistics().apply {
            likes = 0
            shareCount = 0
            viewCount = 0
        }
    }
}
