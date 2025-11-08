package ma.enset.conferenceservice;

import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.entities.Review;
import ma.enset.conferenceservice.enums.TypeConference;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import ma.enset.conferenceservice.repositories.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class ConferenceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceServiceApplication.class, args);
    }
    
    @Bean
    CommandLineRunner initDatabase(ConferenceRepository conferenceRepository, 
                                   ReviewRepository reviewRepository) {
        return args -> {
            // Créer des conférences
            Conference conf1 = Conference.builder()
                    .titre("Architecture Microservices avec Spring Cloud")
                    .type(TypeConference.ACADEMIQUE)
                    .date(LocalDate.of(2024, 6, 15))
                    .duree(180)
                    .nombreInscrits(150)
                    .score(4.5)
                    .keynoteId(1L)
                    .build();
            
            Conference conf2 = Conference.builder()
                    .titre("Clean Code et Design Patterns")
                    .type(TypeConference.COMMERCIALE)
                    .date(LocalDate.of(2024, 7, 20))
                    .duree(120)
                    .nombreInscrits(200)
                    .score(4.8)
                    .keynoteId(3L)
                    .build();
            
            Conference conf3 = Conference.builder()
                    .titre("Domain-Driven Design en Pratique")
                    .type(TypeConference.ACADEMIQUE)
                    .date(LocalDate.of(2024, 8, 10))
                    .duree(240)
                    .nombreInscrits(100)
                    .score(4.7)
                    .keynoteId(4L)
                    .build();
            
            conferenceRepository.saveAll(List.of(conf1, conf2, conf3));
            
            // Créer des reviews pour les conférences
            Review review1 = Review.builder()
                    .date(LocalDateTime.now().minusDays(5))
                    .texte("Excellente présentation sur les microservices!")
                    .note(5)
                    .conference(conf1)
                    .build();
            
            Review review2 = Review.builder()
                    .date(LocalDateTime.now().minusDays(3))
                    .texte("Très instructif et bien structuré")
                    .note(4)
                    .conference(conf1)
                    .build();
            
            Review review3 = Review.builder()
                    .date(LocalDateTime.now().minusDays(2))
                    .texte("Clean Code magistralement expliqué")
                    .note(5)
                    .conference(conf2)
                    .build();
            
            reviewRepository.saveAll(List.of(review1, review2, review3));
            
            System.out.println("Base de données initialisée avec des conférences et reviews");
        };
    }

}
