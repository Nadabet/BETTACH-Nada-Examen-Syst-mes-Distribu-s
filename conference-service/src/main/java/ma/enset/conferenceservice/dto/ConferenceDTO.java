package ma.enset.conferenceservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.conferenceservice.enums.TypeConference;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceDTO {
    
    private Long id;
    
    @NotBlank(message = "Le titre est obligatoire")
    private String titre;
    
    @NotNull(message = "Le type est obligatoire")
    private TypeConference type;
    
    @NotNull(message = "La date est obligatoire")
    private LocalDate date;
    
    @NotNull(message = "La durée est obligatoire")
    @Min(value = 30, message = "La durée minimale est de 30 minutes")
    private Integer duree;
    
    @Min(value = 0, message = "Le nombre d'inscrits doit être positif")
    private Integer nombreInscrits;
    
    @DecimalMin(value = "0.0", message = "Le score doit être entre 0 et 5")
    @DecimalMax(value = "5.0", message = "Le score doit être entre 0 et 5")
    private Double score;
    
    @NotNull(message = "Le keynote ID est obligatoire")
    private Long keynoteId;
    
    // Informations du keynote (récupérées via OpenFeign)
    private KeynoteDTO keynote;
    
}
