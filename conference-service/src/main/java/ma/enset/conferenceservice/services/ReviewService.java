package ma.enset.conferenceservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.conferenceservice.dto.ReviewDTO;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.entities.Review;
import ma.enset.conferenceservice.exceptions.ResourceNotFoundException;
import ma.enset.conferenceservice.mappers.ReviewMapper;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import ma.enset.conferenceservice.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final ConferenceRepository conferenceRepository;
    private final ReviewMapper reviewMapper;
    
    public List<ReviewDTO> getReviewsByConference(Long conferenceId) {
        log.info("Récupération des reviews pour la conférence: {}", conferenceId);
        
        if (!conferenceRepository.existsById(conferenceId)) {
            throw new ResourceNotFoundException("Conférence non trouvée avec l'id: " + conferenceId);
        }
        
        List<Review> reviews = reviewRepository.findByConferenceIdOrderByDateDesc(conferenceId);
        return reviewMapper.toDTOList(reviews);
    }
    
    public ReviewDTO getReviewById(Long id) {
        log.info("Récupération du review avec l'id: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review non trouvé avec l'id: " + id));
        return reviewMapper.toDTO(review);
    }
    
    public ReviewDTO createReview(Long conferenceId, ReviewDTO reviewDTO) {
        log.info("Création d'un nouveau review pour la conférence: {}", conferenceId);
        
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Conférence non trouvée avec l'id: " + conferenceId));
        
        Review review = reviewMapper.toEntity(reviewDTO);
        review.setConference(conference);
        review.setDate(LocalDateTime.now());
        
        Review savedReview = reviewRepository.save(review);
        
        // Recalculer le score moyen de la conférence
        updateConferenceScore(conference);
        
        log.info("Review créé avec succès avec l'id: {}", savedReview.getId());
        return reviewMapper.toDTO(savedReview);
    }
    
    public void deleteReview(Long id) {
        log.info("Suppression du review avec l'id: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review non trouvé avec l'id: " + id));
        
        Conference conference = review.getConference();
        reviewRepository.deleteById(id);
        
        // Recalculer le score de la conférence
        updateConferenceScore(conference);
        
        log.info("Review supprimé avec succès");
    }
    
    private void updateConferenceScore(Conference conference) {
        List<Review> reviews = reviewRepository.findByConferenceId(conference.getId());
        
        if (reviews.isEmpty()) {
            conference.setScore(0.0);
        } else {
            double averageScore = reviews.stream()
                    .mapToInt(Review::getNote)
                    .average()
                    .orElse(0.0);
            conference.setScore(Math.round(averageScore * 10.0) / 10.0);
        }
        
        conferenceRepository.save(conference);
        log.info("Score de la conférence mis à jour: {}", conference.getScore());
    }
    
}
