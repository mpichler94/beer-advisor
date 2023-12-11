package io.github.mpichler94.beeradvisor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@SpringBootApplication(scanBasePackages = ["io.github.mpichler94.beeradvisor"])
class BeerAdvisorApplication

fun main(args: Array<String>) {
    runApplication<BeerAdvisorApplication>(*args)
}
