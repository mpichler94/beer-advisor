package io.github.mpichler94.beeradvisor.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import org.springframework.security.core.GrantedAuthority

@Entity(name = "Authorities")
class BeerAuthority(
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(name = "authority_sequence", sequenceName = "authority_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_sequence")
    private val id: Long,
    private val authority: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    private val user: BeerUser,
) : GrantedAuthority {
    override fun getAuthority(): String {
        TODO("Not yet implemented")
    }
}
