package io.github.mpichler94.beeradvisor.review

import io.github.mpichler94.beeradvisor.user.BeerUser
import jakarta.persistence.*

@Entity
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val author: BeerUser,
    val stars: Int,
    val content: String
)