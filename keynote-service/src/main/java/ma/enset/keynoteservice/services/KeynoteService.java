package ma.enset.keynoteservice.services;

import ma.enset.keynoteservice.dto.KeynoteDTO;
import ma.enset.keynoteservice.entities.Keynote;
import ma.enset.keynoteservice.exceptions.ResourceNotFoundException;
import ma.enset.keynoteservice.exceptions.DuplicateResourceException;
import ma.enset.keynoteservice.mappers.KeynoteMapper;
import ma.enset.keynoteservice.repositories.KeynoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class KeynoteService {
    
    private final KeynoteRepository keynoteRepository;
    private final KeynoteMapper keynoteMapper;
    
    public List<KeynoteDTO> getAllKeynotes() {
        log.info("Récupération de tous les keynotes");
        List<Keynote> keynotes = keynoteRepository.findAll();
        return keynoteMapper.toDTOList(keynotes);
    }
    
    public KeynoteDTO getKeynoteById(Long id) {
        log.info("Récupération du keynote avec l'id: {}", id);
        Keynote keynote = keynoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Keynote non trouvé avec l'id: " + id));
        return keynoteMapper.toDTO(keynote);
    }
    
    public KeynoteDTO createKeynote(KeynoteDTO keynoteDTO) {
        log.info("Création d'un nouveau keynote: {}", keynoteDTO.getEmail());
        
        if (keynoteRepository.existsByEmail(keynoteDTO.getEmail())) {
            throw new DuplicateResourceException("Un keynote avec cet email existe déjà: " + keynoteDTO.getEmail());
        }
        
        Keynote keynote = keynoteMapper.toEntity(keynoteDTO);
        Keynote savedKeynote = keynoteRepository.save(keynote);
        log.info("Keynote créé avec succès avec l'id: {}", savedKeynote.getId());
        
        return keynoteMapper.toDTO(savedKeynote);
    }
    
    public KeynoteDTO updateKeynote(Long id, KeynoteDTO keynoteDTO) {
        log.info("Mise à jour du keynote avec l'id: {}", id);
        
        Keynote keynote = keynoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Keynote non trouvé avec l'id: " + id));
        
        // Vérifier si l'email est déjà utilisé par un autre keynote
        if (keynoteDTO.getEmail() != null && !keynoteDTO.getEmail().equals(keynote.getEmail())) {
            if (keynoteRepository.existsByEmail(keynoteDTO.getEmail())) {
                throw new DuplicateResourceException("Un keynote avec cet email existe déjà: " + keynoteDTO.getEmail());
            }
        }
        
        keynoteMapper.updateEntityFromDTO(keynoteDTO, keynote);
        Keynote updatedKeynote = keynoteRepository.save(keynote);
        log.info("Keynote mis à jour avec succès");
        
        return keynoteMapper.toDTO(updatedKeynote);
    }
    
    public void deleteKeynote(Long id) {
        log.info("Suppression du keynote avec l'id: {}", id);
        
        if (!keynoteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Keynote non trouvé avec l'id: " + id);
        }
        
        keynoteRepository.deleteById(id);
        log.info("Keynote supprimé avec succès");
    }
    
}
