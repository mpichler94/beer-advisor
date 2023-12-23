package io.github.mpichler94.beeradvisor.util

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*


@Component
class MdcFilter() : OncePerRequestFilter() {
    private val requestHeader = "X-Header-Token"
    private val responseHeader = "Response_Token"
    private val mdcTokenKey = "requestId"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
        val token = if (!requestHeader.isNullOrEmpty() && !request.getHeader(requestHeader).isNullOrEmpty()) {
            request.getHeader(requestHeader)
        } else {
            UUID.randomUUID().toString().replace("-", "")
        }
        MDC.put(mdcTokenKey, token)
        if (!responseHeader.isNullOrEmpty()) {
            response.addHeader(responseHeader, token)
        }
            filterChain.doFilter(request, response)
        } finally {
            MDC.remove(mdcTokenKey)
        }
    }
}