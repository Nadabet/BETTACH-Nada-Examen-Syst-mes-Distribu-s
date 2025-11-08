package ma.enset.conferenceservice.mappers;

import ma.enset.conferenceservice.dto.ConferenceDTO;
import ma.enset.conferenceservice.entities.Conference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConferenceMapper {
    
    @Mapping(target = "keynote", ignore = true)
    ConferenceDTO toDTO(Conference conference);
    
    @Mapping(target = "reviews", ignore = true)
    Conference toEntity(ConferenceDTO conferenceDTO);
    
    List<ConferenceDTO> toDTOList(List<Conference> conferences);
    
    @Mapping(target = "reviews", ignore = true)
    void updateEntityFromDTO(ConferenceDTO conferenceDTO, @MappingTarget Conference conference);
    
}
