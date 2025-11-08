package ma.enset.keynoteservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.enset.keynoteservice.dto.KeynoteDTO;
import ma.enset.keynoteservice.services.KeynoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keynotes")
@RequiredArgsConstructor
@Tag(name = "Keynote", description = "API de gestion des Keynote Speakers")
@CrossOrigin(origins = "*")
public class KeynoteController {
    
    private final KeynoteService keynoteService;
    
    @GetMapping
    @Operation(summary = "Récupérer tous les keynotes", description = "Retourne la liste de tous les keynote speakers")
    public ResponseEntity<List<KeynoteDTO>> getAllKeynotes() {
        List<KeynoteDTO> keynotes = keynoteService.getAllKeynotes();
        return ResponseEntity.ok(keynotes);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un keynote par ID", description = "Retourne les détails d'un keynote speaker")
    public ResponseEntity<KeynoteDTO> getKeynoteById(@PathVariable Long id) {
        KeynoteDTO keynote = keynoteService.getKeynoteById(id);
        return ResponseEntity.ok(keynote);
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau keynote", description = "Crée un nouveau keynote speaker")
    public ResponseEntity<KeynoteDTO> createKeynote(@Valid @RequestBody KeynoteDTO keynoteDTO) {
        KeynoteDTO createdKeynote = keynoteService.createKeynote(keynoteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdKeynote);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un keynote", description = "Met à jour les informations d'un keynote speaker")
    public ResponseEntity<KeynoteDTO> updateKeynote(
            @PathVariable Long id,
            @Valid @RequestBody KeynoteDTO keynoteDTO) {
        KeynoteDTO updatedKeynote = keynoteService.updateKeynote(id, keynoteDTO);
        return ResponseEntity.ok(updatedKeynote);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un keynote", description = "Supprime un keynote speaker")
    public ResponseEntity<Void> deleteKeynote(@PathVariable Long id) {
        keynoteService.deleteKeynote(id);
        return ResponseEntity.noContent().build();
    }
    
}
