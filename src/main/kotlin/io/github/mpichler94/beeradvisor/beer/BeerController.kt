package io.github.mpichler94.beeradvisor.beer

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping(value = ["/api/beer"], produces = [MediaType.APPLICATION_JSON_VALUE])
class BeerController(private val repository: BeerRepository, private val breweryRepository: BreweryRepository) {
    @PostMapping
    private fun createBeer(
        @RequestBody beer: BeerDto,
    ): BeerDto {
        val brewery = breweryRepository.findById(beer.breweryId)
        Assert.state(brewery.isPresent, "Brewery ${beer.breweryId} not found")

        val created = repository.save(Beer(null, beer.name, brewery.get(), beer.type, beer.alcohol, beer.description))

        return BeerDto(
            created.name,
            created.type,
            created.brewery.id!!,
            created.alcohol,
            created.description,
            created.reviews,
            created.stars,
        )
    }

    @GetMapping
    private fun beers(): Iterable<BeerDto> {
        return repository.findAll().map { BeerDto(it.name, it.type, it.brewery.id!!, it.alcohol, it.description, it.reviews, it.stars) }
    }

    @GetMapping("/{beerId}")
    private fun beer(
        @PathVariable beerId: Long,
    ): BeerDto {
        val beer = repository.findById(beerId).getOrNull() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return BeerDto(beer.name, beer.type, beer.brewery.id!!, beer.alcohol, beer.description, beer.reviews, beer.stars)
    }
}

internal class BeerDto(
    val name: String,
    val type: String,
    val breweryId: Long,
    val alcohol: Float,
    val description: String,
    val reviews: Int = 0,
    val stars: Float = 0f,
)
