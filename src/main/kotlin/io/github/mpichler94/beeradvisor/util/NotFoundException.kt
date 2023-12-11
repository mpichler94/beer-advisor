package io.github.mpichler94.beeradvisor.util

class NotFoundException : RuntimeException {

    constructor() : super()

    constructor(message: String) : super(message)

}