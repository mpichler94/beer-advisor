package io.github.mpichler94.beeradvisor.user

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class BeerUserDetailsService(private val repository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return repository.findByUsername(username!!).getOrElse { throw UsernameNotFoundException(username) }
    }
}