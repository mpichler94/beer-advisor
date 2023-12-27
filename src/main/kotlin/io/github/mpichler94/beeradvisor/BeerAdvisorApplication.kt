package io.github.mpichler94.beeradvisor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BeerAdvisorApplication

fun main(args: Array<String>) {
    runApplication<BeerAdvisorApplication>(*args)
}
