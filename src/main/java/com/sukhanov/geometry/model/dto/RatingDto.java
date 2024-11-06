package com.sukhanov.geometry.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Рейтинг")
public class RatingDto {

    @Schema(description = "Общая сумма")
    @Min(0)
    private long totalSum;

    @Schema(description = "Количество проголосовавших")
    @Min(0)
    private long amount;
}

