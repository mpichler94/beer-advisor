package io.github.mpichler94.beeradvisor.beer

import org.springframework.data.repository.CrudRepository

interface BreweryRepository : CrudRepository<Brewery, Long>
