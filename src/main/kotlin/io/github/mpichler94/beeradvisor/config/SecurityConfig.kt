package io.github.mpichler94.beeradvisor.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.context.DelegatingSecurityContextRepository
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.net.URI

@Configuration
@EnableWebSecurity
class SecurityConfig(private val mapper: ObjectMapper) {
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
                securityContextRepository =
                    DelegatingSecurityContextRepository(
                        RequestAttributeSecurityContextRepository(),
                        HttpSessionSecurityContextRepository(),
                    )
            }
            formLogin {
                loginProcessingUrl = "/api/user/login"
                authenticationSuccessHandler =
                    AuthenticationSuccessHandler {
                            _,
                            response,
                            _,
                        ->
                        response.status = HttpServletResponse.SC_OK
                    }
                authenticationFailureHandler =
                    AuthenticationFailureHandler { _, response, exception ->
                        val detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid credentials provided")
                        detail.type = URI.create("about:blank")
                        detail.title = "Bad Credentials"
                        detail.properties = mapOf("message" to exception.message)
                        response.status = HttpServletResponse.SC_BAD_REQUEST
                        mapper.writeValue(response.writer, detail)
                    }
                permitAll = true
            }
            exceptionHandling {
                authenticationEntryPoint = Http403ForbiddenEntryPoint()
            }
            logout {
                logoutUrl = "/api/user/logout"
                logoutSuccessHandler = LogoutSuccessHandler { _, response, _ -> response.status = HttpServletResponse.SC_OK }
            }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config =
            CorsConfiguration().apply {
                allowedOrigins = listOf("http://localhost:8081")
                allowedMethods = listOf("*")
                allowedHeaders = listOf("*")
                allowCredentials = true
            }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}
