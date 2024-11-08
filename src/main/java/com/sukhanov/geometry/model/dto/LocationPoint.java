package com.sukhanov.geometry.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Координаты")
public class LocationPoint {

        @JsonProperty("longitudeX")
        @Schema(description = "Долгота", implementation = Double.class, example = "44.530353")
        private Double longitudeX;

        @JsonProperty("latitudeY")
        @Schema(description = "Широта", implementation = Double.class, example = "48.716496")
        private Double latitudeY;
}