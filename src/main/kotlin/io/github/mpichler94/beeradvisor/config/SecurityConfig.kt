package io.github.mpichler94.beeradvisor.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint
import org.springframework.security.web.context.DelegatingSecurityContextRepository
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors {}
            csrf { disable() } // FIXME: Enable in production
            authorizeRequests {
                authorize("/api/user/**", permitAll)
                authorize("/api/**", authenticated)
                authorize("/**", permitAll)
            }
            securityContext {
                securityContextRepository = DelegatingSecurityContextRepository(
                    RequestAttributeSecurityContextRepository(),
                    HttpSessionSecurityContextRepository()
                )
            }
            formLogin {
                loginProcessingUrl = "/api/user/login"
                defaultSuccessUrl("/api/user", true)
                permitAll = true
            }
            exceptionHandling {
                authenticationEntryPoint = Http403ForbiddenEntryPoint()
            }
            logout {
                logoutUrl = "/api/user/logout"
                logoutSuccessUrl = "/api/user"
            }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:8081")
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }

    //@Bean
    fun userDetailsService(): UserDetailsService {
        val users = User.withDefaultPasswordEncoder()
        val user = users
            .username("user")
            .password("password")
            .roles("USER")
            .build()
        val admin = users
            .username("admin")
            .password("admin")
            .roles("USER", "ADMIN")
            .build()

        return InMemoryUserDetailsManager(user, admin)
    }
}