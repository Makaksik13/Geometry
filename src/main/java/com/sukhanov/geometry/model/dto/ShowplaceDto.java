package com.sukhanov.geometry.model.dto;

import com.sukhanov.geometry.model.entity.Category;
import com.sukhanov.geometry.validation.Marker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
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

    @Schema(description = "Идентификатор")
    @Null(groups = Marker.OnCreate.class, message = "The id '${validatedValue}' must be null")
    @NotNull(groups = Marker.OnUpdate.class, message = "The id must be not null")
    private UUID id;

    @Schema(description = "Город", example = "Самара")
    @NotBlank(message = "The city '${validatedValue}' must be not blank")
    @Size(max = 64, message = "The city '${validatedValue}' must be no more than {max}")
    private String city;

    @Schema(description = "Описание")
    @NotBlank(message = "The city '${validatedValue}' must be not blank")
    @Size(max = 64, message = "The city '${validatedValue}' must be no more than {max}")
    private String description;

    @Schema(description = "Категория")
    @NotNull(message = "The category must be not null")
    private Category category;

    @Valid
    private List<CommentDto> comments;

    @Valid
    private RatingDto rating;

    @Valid
    private LocationPoint location;
}
