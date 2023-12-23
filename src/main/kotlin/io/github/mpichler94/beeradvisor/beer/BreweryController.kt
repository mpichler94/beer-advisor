package io.github.mpichler94.beeradvisor.beer

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping(value = ["/api/brewery"], produces = [MediaType.APPLICATION_JSON_VALUE])
class BreweryController(private val repository: BreweryRepository) {

    @PostMapping
    private fun createBrewery(@RequestBody brewery: BreweryDto): BreweryDto {
        val created = repository.save(Brewery(null, brewery.name))

        return BreweryDto(created.name)
    }

    @GetMapping
    private fun breweries(): Iterable<BreweryDto> {
        return repository.findAll().map { BreweryDto(it.name) }
    }

    @GetMapping("/{breweryId}")
    private fun brewery(@PathVariable breweryId: Long): BreweryDto {
        val brewery = repository.findById(breweryId).getOrNull() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        return BreweryDto(brewery.name)
    }
}

internal class BreweryDto(val name: String)