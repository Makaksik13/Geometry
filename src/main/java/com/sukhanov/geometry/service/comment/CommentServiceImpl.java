package com.sukhanov.geometry.service.comment;

import com.sukhanov.geometry.mapper.CommentMapper;
import com.sukhanov.geometry.model.dto.CommentDto;
import com.sukhanov.geometry.model.entity.Comment;
import com.sukhanov.geometry.repository.CommentRepository;
import com.sukhanov.geometry.validation.Validator.showplace.ShowplaceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final ShowplaceValidator showplaceValidator;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(UUID showplaceId, String content){
        showplaceValidator.validateShowplaceExistence(showplaceId);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setShowplaceId(showplaceId);

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    @Override
    public Page<CommentDto> getCommentsByShowplaceId(Integer offset, Integer limit, UUID showplaceId){
        return commentRepository.findAllByShowplaceId(showplaceId, PageRequest.of(offset, limit))
                .map(commentMapper::toDto);
    }
}
