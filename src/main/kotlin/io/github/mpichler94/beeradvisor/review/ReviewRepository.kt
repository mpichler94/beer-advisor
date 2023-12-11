package io.github.mpichler94.beeradvisor.review

import org.springframework.data.repository.CrudRepository

interface ReviewRepository : CrudRepository<Review, Long>
