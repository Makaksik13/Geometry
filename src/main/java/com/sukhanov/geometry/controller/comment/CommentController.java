package com.sukhanov.geometry.controller.comment;

import com.sukhanov.geometry.model.dto.CommentDto;
import com.sukhanov.geometry.response.ErrorResponse;
import com.sukhanov.geometry.response.ValidationErrorResponse;
import com.sukhanov.geometry.service.comment.CommentService;
import com.sukhanov.geometry.validation.Marker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(
        name="Контроллер комментариев",
        description="Взаимодействие с комментариями достопримечательностей"
)
@ApiResponses({
        @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Достопримечательность не найдена",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400",
                content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
})
@Validated
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Создание комментария у достопримечательности")
    @Validated({Marker.OnCreate.class})
    @PostMapping("/showplace/{showplaceId}")
    public CommentDto createComment(@NotNull @PathVariable UUID showplaceId,
                                    @NotBlank @RequestBody String content){
        return commentService.createComment(showplaceId, content);
    }

    @Operation(summary = "Получение комментариев достопримечательности")
    @GetMapping("/showplace/{showplaceId}")
    public Page<CommentDto> getComments(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") @Min(1) Integer limit,
            @NotNull @PathVariable UUID showplaceId
    ){
        return commentService.getCommentsByShowplaceId(offset, limit, showplaceId);
    }
}
