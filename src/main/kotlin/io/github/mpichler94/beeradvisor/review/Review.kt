package io.github.mpichler94.beeradvisor.review

import io.github.mpichler94.beeradvisor.beer.Beer
import io.github.mpichler94.beeradvisor.user.BeerUser
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator

@Entity(name = "Reviews")
class Review(
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(name = "review_sequence", sequenceName = "review_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_sequence")
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val author: BeerUser,
    @ManyToOne
    @JoinColumn(name = "beer_id")
    val beer: Beer,
    val stars: Int,
    val content: String = "",
)
