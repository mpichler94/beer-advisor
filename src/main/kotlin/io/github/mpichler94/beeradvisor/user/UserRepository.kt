package io.github.mpichler94.beeradvisor.user

import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository : CrudRepository<BeerUser, Long> {
    fun findByUsername(username: String): Optional<BeerUser>
}
