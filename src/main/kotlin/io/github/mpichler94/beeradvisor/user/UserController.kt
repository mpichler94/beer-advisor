package io.github.mpichler94.beeradvisor.user

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userRepository: UserRepository,
    private val authorityRepository: AuthorityRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @PostMapping("/register")
    private fun createUser(
        @RequestBody user: UserDto,
    ): UserDto {
        if (userRepository.findByUsername(user.username).isPresent) {
            throw IllegalStateException("Username already exists")
        }

        val authority = authorityRepository.findByAuthority("user")
        if (authority.isEmpty) {
            throw java.lang.IllegalStateException("Authority does not exist")
        }
        val createdUser =
            userRepository.save(
                BeerUser(
                    null,
                    user.username,
                    passwordEncoder.encode(user.password),
                    setOf(authority.get()),
                ),
            )
        return UserDto(createdUser.username)
    }

    @GetMapping
    private fun user(request: HttpServletRequest): UserDto {
        return UserDto(request.userPrincipal?.name ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED))
    }
}

internal class UserDto(val username: String, val password: String? = null)
