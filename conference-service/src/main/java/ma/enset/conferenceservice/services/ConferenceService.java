package ma.enset.conferenceservice.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.conferenceservice.clients.KeynoteRestClient;
import ma.enset.conferenceservice.dto.ConferenceDTO;
import ma.enset.conferenceservice.dto.KeynoteDTO;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.exceptions.ResourceNotFoundException;
import ma.enset.conferenceservice.mappers.ConferenceMapper;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConferenceService {
    
    private final ConferenceRepository conferenceRepository;
    private final ConferenceMapper conferenceMapper;
    private final KeynoteRestClient keynoteRestClient;
    
    public List<ConferenceDTO> getAllConferences() {
        log.info("Récupération de toutes les conférences");
        List<Conference> conferences = conferenceRepository.findAll();
        return conferences.stream()
                .map(this::mapConferenceWithKeynote)
                .collect(Collectors.toList());
    }
    
    public ConferenceDTO getConferenceById(Long id) {
        log.info("Récupération de la conférence avec l'id: {}", id);
        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conférence non trouvée avec l'id: " + id));
        return mapConferenceWithKeynote(conference);
    }
    
    public ConferenceDTO createConference(ConferenceDTO conferenceDTO) {
        log.info("Création d'une nouvelle conférence: {}", conferenceDTO.getTitre());
        
        // Vérifier que le keynote existe
        try {
            keynoteRestClient.getKeynoteById(conferenceDTO.getKeynoteId());
        } catch (Exception e) {
            log.warn("Impossible de vérifier l'existence du keynote: {}", e.getMessage());
        }
        
        Conference conference = conferenceMapper.toEntity(conferenceDTO);
        Conference savedConference = conferenceRepository.save(conference);
        log.info("Conférence créée avec succès avec l'id: {}", savedConference.getId());
        
        return mapConferenceWithKeynote(savedConference);
    }
    
    public ConferenceDTO updateConference(Long id, ConferenceDTO conferenceDTO) {
        log.info("Mise à jour de la conférence avec l'id: {}", id);
        
        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conférence non trouvée avec l'id: " + id));
        
        conferenceMapper.updateEntityFromDTO(conferenceDTO, conference);
        Conference updatedConference = conferenceRepository.save(conference);
        log.info("Conférence mise à jour avec succès");
        
        return mapConferenceWithKeynote(updatedConference);
    }
    
    public void deleteConference(Long id) {
        log.info("Suppression de la conférence avec l'id: {}", id);
        
        if (!conferenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Conférence non trouvée avec l'id: " + id);
        }
        
        conferenceRepository.deleteById(id);
        log.info("Conférence supprimée avec succès");
    }
    
    @CircuitBreaker(name = "keynoteService", fallbackMethod = "mapConferenceWithoutKeynote")
    private ConferenceDTO mapConferenceWithKeynote(Conference conference) {
        ConferenceDTO conferenceDTO = conferenceMapper.toDTO(conference);
        
        try {
            KeynoteDTO keynote = keynoteRestClient.getKeynoteById(conference.getKeynoteId());
            conferenceDTO.setKeynote(keynote);
        } catch (Exception e) {
            log.warn("Impossible de récupérer les informations du keynote: {}", e.getMessage());
        }
        
        return conferenceDTO;
    }
    
    private ConferenceDTO mapConferenceWithoutKeynote(Conference conference, Exception exception) {
        log.warn("Fallback: Mapping conférence sans keynote: {}", exception.getMessage());
        return conferenceMapper.toDTO(conference);
    }
    
}
