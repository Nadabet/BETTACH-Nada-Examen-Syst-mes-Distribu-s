package ma.enset.conferenceservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.enset.conferenceservice.dto.ReviewDTO;
import ma.enset.conferenceservice.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conferences/{conferenceId}/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "API de gestion des Reviews de Conférences")
@CrossOrigin(origins = "*")
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @GetMapping
    @Operation(summary = "Récupérer les reviews d'une conférence", description = "Retourne la liste des reviews pour une conférence")
    public ResponseEntity<List<ReviewDTO>> getReviewsByConference(@PathVariable Long conferenceId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByConference(conferenceId);
        return ResponseEntity.ok(reviews);
    }
    
    @PostMapping
    @Operation(summary = "Ajouter un review", description = "Ajoute un nouveau review à une conférence")
    public ResponseEntity<ReviewDTO> createReview(
            @PathVariable Long conferenceId,
            @Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(conferenceId, reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }
    
    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Supprimer un review", description = "Supprime un review d'une conférence")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long conferenceId,
            @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
    
}
