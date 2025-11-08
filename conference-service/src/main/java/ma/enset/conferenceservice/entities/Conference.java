package ma.enset.conferenceservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.conferenceservice.enums.TypeConference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conference {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le titre est obligatoire")
    @Column(nullable = false)
    private String titre;
    
    @NotNull(message = "Le type est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeConference type;
    
    @NotNull(message = "La date est obligatoire")
    @Column(nullable = false)
    private LocalDate date;
    
    @NotNull(message = "La durée est obligatoire")
    @Min(value = 30, message = "La durée minimale est de 30 minutes")
    @Column(nullable = false)
    private Integer duree; // en minutes
    
    @Min(value = 0, message = "Le nombre d'inscrits doit être positif")
    @Column(nullable = false)
    private Integer nombreInscrits = 0;
    
    @DecimalMin(value = "0.0", message = "Le score doit être entre 0 et 5")
    @DecimalMax(value = "5.0", message = "Le score doit être entre 0 et 5")
    private Double score = 0.0;
    
    @NotNull(message = "Le keynote ID est obligatoire")
    @Column(nullable = false)
    private Long keynoteId;
    
    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    
}
