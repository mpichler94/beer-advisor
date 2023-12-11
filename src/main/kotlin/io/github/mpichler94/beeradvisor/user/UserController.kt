package io.github.mpichler94.beeradvisor.user

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userRepository: UserRepository,
    private val authorityRepository: AuthorityRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    private fun createUser(@RequestBody user: UserDto): UserDto {
        if (userRepository.findByUsername(user.username).isPresent) {
            throw IllegalStateException("Username already exists")
        }

        val authority = authorityRepository.findByAuthority("user")
        if (authority.isEmpty) {
            throw java.lang.IllegalStateException("Authority does not exist")
        }
        val createdUser = userRepository.save(
            BeerUser(
                0,
                user.username,
                passwordEncoder.encode(user.password),
                true,
                true,
                true,
                setOf()
            )
        )
        return UserDto(createdUser.username, createdUser.password)
    }

    @GetMapping
    fun user(request: HttpServletRequest): String {
        return request.userPrincipal?.name ?: ""
    }
}

internal class UserDto(val username: String, val password: String)