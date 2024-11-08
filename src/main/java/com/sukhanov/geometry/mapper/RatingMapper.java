package com.sukhanov.geometry.mapper;

import com.sukhanov.geometry.model.dto.RatingDto;
import com.sukhanov.geometry.model.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RatingMapper {

    @Mapping(target = "value", qualifiedByName = "calculateRatingValue", source = ".")
    RatingDto toDto(Rating rating);

    Rating toEntity(RatingDto ratingDto);

    @Named("calculateRatingValue")
    default Double calculateRatingValue(Rating rating){
        if(rating.getAmount() == 0) return 0.0;

        return Math.round(((double) rating.getTotalSum() / rating.getAmount()) * 100) / 100.0;
    }
}
