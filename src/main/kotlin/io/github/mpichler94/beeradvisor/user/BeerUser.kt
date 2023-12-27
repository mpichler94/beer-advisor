package io.github.mpichler94.beeradvisor.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "Users")
class BeerUser(
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    val id: Long?,
    private val username: String,
    private val password: String,
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private val authorities: Set<BeerAuthority> = setOf(),
    @Column(columnDefinition = "boolean default true")
    private val accountNonExpired: Boolean = true,
    @Column(columnDefinition = "boolean default true")
    private val accountNonLocked: Boolean = true,
    @Column(columnDefinition = "boolean default true")
    private val credentialsNonExpired: Boolean = true,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities.toMutableSet()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
