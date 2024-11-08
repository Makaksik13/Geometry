package com.sukhanov.geometry.service.comment;

import com.sukhanov.geometry.model.dto.CommentDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CommentService {

    CommentDto createComment(UUID showplaceId, String content);

    Page<CommentDto> getCommentsByShowplaceId(Integer offset, Integer limit, UUID showplaceId);
}
