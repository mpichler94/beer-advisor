package io.github.mpichler94.beeradvisor.user

import org.springframework.data.repository.CrudRepository
import java.util.*

interface AuthorityRepository : CrudRepository<BeerAuthority, Long> {
    fun findByAuthority(authority: String): Optional<BeerAuthority>
}