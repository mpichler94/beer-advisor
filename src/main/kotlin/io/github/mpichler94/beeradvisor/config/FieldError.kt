package io.github.mpichler94.beeradvisor.config

data class FieldError(
    var `field`: String? = null,
    var errorCode: String? = null,
)
