package ma.enset.conferenceservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.enset.conferenceservice.dto.ConferenceDTO;
import ma.enset.conferenceservice.services.ConferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conferences")
@RequiredArgsConstructor
@Tag(name = "Conference", description = "API de gestion des Conférences")
@CrossOrigin(origins = "*")
public class ConferenceController {
    
    private final ConferenceService conferenceService;
    
    @GetMapping
    @Operation(summary = "Récupérer toutes les conférences", description = "Retourne la liste de toutes les conférences avec leurs keynotes")
    public ResponseEntity<List<ConferenceDTO>> getAllConferences() {
        List<ConferenceDTO> conferences = conferenceService.getAllConferences();
        return ResponseEntity.ok(conferences);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une conférence par ID", description = "Retourne les détails d'une conférence avec son keynote")
    public ResponseEntity<ConferenceDTO> getConferenceById(@PathVariable Long id) {
        ConferenceDTO conference = conferenceService.getConferenceById(id);
        return ResponseEntity.ok(conference);
    }
    
    @PostMapping
    @Operation(summary = "Créer une nouvelle conférence", description = "Crée une nouvelle conférence")
    public ResponseEntity<ConferenceDTO> createConference(@Valid @RequestBody ConferenceDTO conferenceDTO) {
        ConferenceDTO createdConference = conferenceService.createConference(conferenceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConference);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une conférence", description = "Met à jour les informations d'une conférence")
    public ResponseEntity<ConferenceDTO> updateConference(
            @PathVariable Long id,
            @Valid @RequestBody ConferenceDTO conferenceDTO) {
        ConferenceDTO updatedConference = conferenceService.updateConference(id, conferenceDTO);
        return ResponseEntity.ok(updatedConference);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une conférence", description = "Supprime une conférence")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        conferenceService.deleteConference(id);
        return ResponseEntity.noContent().build();
    }
    
}
