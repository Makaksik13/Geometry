package com.sukhanov.geometry.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {

    private UUID id;

    @NotNull
    private String content;

    @PastOrPresent
    private LocalDateTime createdAt;
}
