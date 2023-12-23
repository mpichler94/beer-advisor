package io.github.mpichler94.beeradvisor.beer

import jakarta.persistence.*

@Entity(name = "Breweries")
class Brewery (
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(name="brewery_sequence", sequenceName = "brewery_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brewery_sequence")
    val id: Long?,
    val name: String,
    @OneToMany(mappedBy = "brewery", fetch = FetchType.EAGER)
    val beers: Set<Beer> = setOf(),
 )