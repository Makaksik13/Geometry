package com.sukhanov.geometry.controller.comment;

import com.sukhanov.geometry.model.dto.CommentDto;
import com.sukhanov.geometry.service.comment.CommentService;
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

@Validated
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/showplace/{showplaceId}")
    public CommentDto createComment(@NotNull @PathVariable UUID showplaceId, @NotBlank @RequestBody String content){
        return commentService.createComment(showplaceId, content);
    }

    @GetMapping("/showplace/{showplaceId}")
    public Page<CommentDto> getComments(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") @Min(1) Integer limit,
            @NotNull @PathVariable UUID showplaceId
    ){
        return commentService.getCommentsByShowplaceId(offset, limit, showplaceId);
    }
}
