package io.github.mpichler94.beeradvisor.beer

import jakarta.persistence.*

@Entity(name = "Beers")
class Beer (
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(name="beer_sequence", sequenceName = "beer_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beer_sequence")
    val id: Long?,
    val name: String,
    @ManyToOne
    @JoinColumn(name = "brewery_id")
    val brewery: Brewery,
    val type: String,
    val alcohol: Float,
    @Column(columnDefinition = "TEXT")
    val description: String,
    val reviews: Int = 0,
    val stars: Float = 0f
)