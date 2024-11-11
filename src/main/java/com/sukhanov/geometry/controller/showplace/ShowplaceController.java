package com.sukhanov.geometry.controller.showplace;

import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.request.RequestModel;
import com.sukhanov.geometry.response.ErrorResponse;
import com.sukhanov.geometry.response.ValidationErrorResponse;
import com.sukhanov.geometry.service.showplace.ShowplaceService;
import com.sukhanov.geometry.validation.Marker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(
        name="Контроллер достопримечательностей",
        description="Взаимодействие с достопримечательностями"
)
@ApiResponses({@ApiResponse(responseCode = "200", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
@Validated
@RestController
@RequestMapping("/showplace")
@RequiredArgsConstructor
public class ShowplaceController {

    private final ShowplaceService showplaceService;

    @ApiResponse(responseCode = "400",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @Operation(summary = "Получение достопримечательностей по фильтрам и в определённом порядке")
    @GetMapping
    public Page<ShowplaceDto> findAllByCriteria(@Valid @ParameterObject RequestModel requestModel){
        return showplaceService.getAllByCriteria(requestModel);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Достопримечательность не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @Operation(summary = "Выставление оценки достопримечательности по её идентификатору")
    @PutMapping("/{uuid}/rating")
    public Double giveRating(@NotNull @PathVariable UUID uuid, @Min(0) @Max(5) @RequestBody Integer mark){
        return showplaceService.giveRating(uuid, mark);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Достопримечательность не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @Operation(summary = "Получение достопримечательности по её идентификатору")
    @GetMapping("/{showplaceId}")
    public ShowplaceDto getShowplaceById(@NotNull UUID showplaceId){
        return showplaceService.getShowplaceById(showplaceId);
    }

    @ApiResponse(responseCode = "400",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @Operation(summary = "Создание достопримечательности")
    @Validated({Marker.OnCreate.class})
    @PostMapping
    public ShowplaceDto createShowplace(@Valid ShowplaceDto showplaceDto){
        return showplaceService.createShowplace(showplaceDto);
    }
}
