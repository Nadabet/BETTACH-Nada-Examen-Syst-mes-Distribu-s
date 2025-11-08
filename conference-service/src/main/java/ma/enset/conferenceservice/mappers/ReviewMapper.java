package ma.enset.conferenceservice.mappers;

import ma.enset.conferenceservice.dto.ReviewDTO;
import ma.enset.conferenceservice.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {
    
    @Mapping(source = "conference.id", target = "conferenceId")
    ReviewDTO toDTO(Review review);
    
    @Mapping(target = "conference", ignore = true)
    Review toEntity(ReviewDTO reviewDTO);
    
    List<ReviewDTO> toDTOList(List<Review> reviews);
    
    @Mapping(target = "conference", ignore = true)
    void updateEntityFromDTO(ReviewDTO reviewDTO, @MappingTarget Review review);
    
}
