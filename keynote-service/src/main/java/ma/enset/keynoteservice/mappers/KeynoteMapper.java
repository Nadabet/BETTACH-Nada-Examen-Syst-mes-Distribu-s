package ma.enset.keynoteservice.mappers;

import ma.enset.keynoteservice.dto.KeynoteDTO;
import ma.enset.keynoteservice.entities.Keynote;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface KeynoteMapper {
    
    KeynoteDTO toDTO(Keynote keynote);
    
    Keynote toEntity(KeynoteDTO keynoteDTO);
    
    List<KeynoteDTO> toDTOList(List<Keynote> keynotes);
    
    void updateEntityFromDTO(KeynoteDTO keynoteDTO, @MappingTarget Keynote keynote);
    
}
