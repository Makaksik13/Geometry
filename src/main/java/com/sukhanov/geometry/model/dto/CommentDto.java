package com.sukhanov.geometry.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Schema(name = "Комментарий")
public class CommentDto {

    @Schema(description = "Идентификатор")
    private UUID id;

    @Schema(description = "Содержание")
    @NotBlank(message = "The content '${validatedValue}' must be not blank")
    @Size(max = 4096, message = "The content '${validatedValue}' must be no more than {max}")
    private String content;

    @Schema(description = "Дата создания")
    @PastOrPresent(message = "The date of birth '${validatedValue}' must not be in the future")
    private LocalDateTime createdAt;
}
