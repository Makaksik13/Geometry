package com.sukhanov.geometry.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Координаты")
public class LocationPoint {

        @JsonProperty("longitudeX")
        @Schema(description = "Долгота", example = "44.530353")
        private Double longitudeX;

        @JsonProperty("latitudeY")
        @Schema(description = "Широта", example = "48.716496")
        private Double latitudeY;
}