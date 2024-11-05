package com.sukhanov.geometry.mapper;

import com.sukhanov.geometry.model.dto.RatingDto;
import com.sukhanov.geometry.model.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RatingMapper {

    RatingDto toDto(Rating rating);

    Rating toEntity(RatingDto ratingDto);
}
