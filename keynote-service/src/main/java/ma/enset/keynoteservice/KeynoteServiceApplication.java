package ma.enset.keynoteservice;

import ma.enset.keynoteservice.entities.Keynote;
import ma.enset.keynoteservice.repositories.KeynoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class KeynoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeynoteServiceApplication.class, args);
    }
    
    @Bean
    CommandLineRunner initDatabase(KeynoteRepository keynoteRepository) {
        return args -> {
            List<Keynote> keynotes = List.of(
                    Keynote.builder()
                            .nom("Youssfi")
                            .prenom("Mohamed")
                            .email("m.youssfi@enset.ma")
                            .fonction("Professeur - Expert Microservices")
                            .build(),
                    Keynote.builder()
                            .nom("Smith")
                            .prenom("John")
                            .email("john.smith@tech.com")
                            .fonction("Chief Technology Officer")
                            .build(),
                    Keynote.builder()
                            .nom("Martin")
                            .prenom("Robert")
                            .email("robert.martin@clean-code.com")
                            .fonction("Software Architect - Clean Code Expert")
                            .build(),
                    Keynote.builder()
                            .nom("Evans")
                            .prenom("Eric")
                            .email("eric.evans@ddd.com")
                            .fonction("Domain-Driven Design Expert")
                            .build()
            );
            
            keynoteRepository.saveAll(keynotes);
            System.out.println("Base de données initialisée avec " + keynotes.size() + " keynotes");
        };
    }

}
