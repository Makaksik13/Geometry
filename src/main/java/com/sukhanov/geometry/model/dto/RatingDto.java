package com.sukhanov.geometry.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RatingDto {

    private long totalSum;

    private long amount;
}

