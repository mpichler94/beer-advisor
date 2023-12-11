package io.github.mpichler94.beeradvisor.review

import io.github.mpichler94.beeradvisor.user.UserRepository
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/api/reviews"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ReviewController(private val repository: ReviewRepository, private val userRepository: UserRepository) {

    @PostMapping
    @ApiResponse(responseCode = "201")
    private fun createReview(
        @RequestBody @Valid review: ReviewDto,
        request: HttpServletRequest
    ): ResponseEntity<ReviewDto> {
        val username = request.userPrincipal.name
        val user = userRepository.findByUsername(username)
        Assert.state(user.isPresent, "User DB corrupt")
        val result = repository.save(Review(0, user.get(), review.stars, review.content))
        return ResponseEntity(
            ReviewDto(result.id, result.author.username, result.content, result.stars),
            HttpStatus.CREATED
        )
    }

    @GetMapping
    private fun reviews(): Iterable<ReviewDto> {
        return repository.findAll().map { ReviewDto(it.id, it.author.username, it.content, it.stars) }
    }


    @GetMapping("/:beerId")
    private fun reviewsForBeer(@RequestParam beerId: Optional<Long>): Iterable<ReviewDto> {
        return repository.findAll().map { ReviewDto(it.id, it.author.username, it.content, it.stars) }
    }

    @GetMapping("/{id}")
    private fun getReview(@PathVariable id: Long): Optional<ReviewDto> {
        return repository.findById(id).map { ReviewDto(it.id, it.author.username, it.content, it.stars) }
    }

    @PutMapping("/{id}")
    private fun updateReview(@PathVariable id: Long, @RequestBody @Valid reviewDTO: ReviewDto): ResponseEntity<Long> {
//        reviewService.update(id, reviewDTO)
        return ResponseEntity.ok(id)
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteReview(@PathVariable(name = "id") id: Long) {
//        reviewService.delete(id)
    }
}

internal class ReviewDto(val id: Long, val author: String?, val content: String, val stars: Int)