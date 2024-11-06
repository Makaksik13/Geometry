package com.sukhanov.geometry.model.request;

import com.sukhanov.geometry.model.dto.LocationPoint;
import com.sukhanov.geometry.model.entity.Category;
import com.sukhanov.geometry.model.sorting.SortedField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Запрос для поиска")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {

    @Schema(description = "местоположение")
    @Valid
    private LocationPoint location;

    @Schema(
            description = "Номер страницы",
            implementation = Integer.class,
            example = "0"
    )
    @NotNull
    @Min(0)
    private Integer offset;

    @Schema(
            description = "Количество записей на странице",
            implementation = Integer.class,
            example = "10",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(0)
    @NotNull
    private Integer limit;

    @Schema(
            description = "Поля сортировки",
            implementation = SortedField[].class,
            example = "[\"DISTANCE\"]")
    @NotEmpty
    private SortedField[] orderBy;

    @Schema(
            description = "Порядки сортировки",
            implementation = String[].class,
            example = "[\"asc\"]")
    @NotEmpty
    private String[] order;

    @Schema(
            description = "Радиус поиска",
            implementation = Double.class,
            example = "100.0",
            minimum = "0.0"
    )
    @NotNull
    @PositiveOrZero
    private Double radius;

    @Schema(
            description = "Категории для поиска",
            implementation = Category[].class
    )
    private List<Category> categories;

    @Schema(
            description = "Минимальный рейтинг",
            implementation = Double.class,
            example = "2.0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    @PositiveOrZero
    private Double rating;
}
