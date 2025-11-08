package ma.enset.keynoteservice.repositories;

import ma.enset.keynoteservice.entities.Keynote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeynoteRepository extends JpaRepository<Keynote, Long> {
    
    Optional<Keynote> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
}
