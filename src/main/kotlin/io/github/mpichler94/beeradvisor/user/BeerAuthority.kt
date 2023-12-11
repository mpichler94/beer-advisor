package io.github.mpichler94.beeradvisor.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
class BeerAuthority(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,
    private val authority: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    private val user: BeerUser
) : GrantedAuthority {

    override fun getAuthority(): String {
        TODO("Not yet implemented")
    }
}
