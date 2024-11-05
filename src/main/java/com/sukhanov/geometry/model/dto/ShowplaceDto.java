package com.sukhanov.geometry.model.dto;

import com.sukhanov.geometry.model.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Достопримечательность")
public class ShowplaceDto {

    private UUID id;

    private String city;

    private String description;

    private Category category;

    private List<CommentDto> comments;

    private RatingDto rating;

    private LocationPoint geometry;
}
