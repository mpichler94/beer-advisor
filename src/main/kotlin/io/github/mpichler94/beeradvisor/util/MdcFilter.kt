package io.github.mpichler94.beeradvisor.util

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.UUID

@Component
class MdcFilter(@Value("\${beer.web.requestHeader:X-Header-Token}") private val requestHeader: String?,
                @Value("\${beer.web.responseHeader:Response_Token}") private val responseHeader: String?,
                @Value("\${beer.web.mdcTokenKey:requestId}") private val mdcTokenKey: String?) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val token =
                if (!requestHeader.isNullOrEmpty() && !request.getHeader(requestHeader).isNullOrEmpty()) {
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
