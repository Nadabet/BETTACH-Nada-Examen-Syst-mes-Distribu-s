package ma.enset.conferenceservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ma.enset.conferenceservice.dto.KeynoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "KEYNOTE-SERVICE")
public interface KeynoteRestClient {
    
    @GetMapping("/api/keynotes/{id}")
    @CircuitBreaker(name = "keynoteService", fallbackMethod = "getDefaultKeynote")
    KeynoteDTO getKeynoteById(@PathVariable Long id);
    
    default KeynoteDTO getDefaultKeynote(Long id, Exception exception) {
        return KeynoteDTO.builder()
                .id(id)
                .nom("Non disponible")
                .prenom("Non disponible")
                .email("non.disponible@example.com")
                .fonction("Information temporairement indisponible")
                .build();
    }
    
}
