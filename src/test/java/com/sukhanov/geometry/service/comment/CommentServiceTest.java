package com.sukhanov.geometry.service.comment;

import com.sukhanov.geometry.exception.NotFoundException;
import com.sukhanov.geometry.mapper.CommentMapper;
import com.sukhanov.geometry.model.dto.CommentDto;
import com.sukhanov.geometry.model.entity.Comment;
import com.sukhanov.geometry.repository.CommentRepository;
import com.sukhanov.geometry.validation.Validator.showplace.ShowplaceValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование CommentService")
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ShowplaceValidatorImpl showplaceValidator;
    @Spy
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    private Comment expectedComment;
    private CommentDto expectedCommentDto;
    private final PageRequest pageRequest = PageRequest.of(1,1);

    @BeforeEach
    public void init(){
        expectedComment = Comment.builder()
                .content("content")
                .showplaceId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .build();

        expectedCommentDto = CommentDto.builder()
                .content("content")
                .build();
    }

    @Nested
    class GetDesign{

        @Test
        @DisplayName("Попытка получить комментарии несуществующей достопримечательности")
        public void testGetCommentsByShowplaceIdWithNotFoundShowplace(){
            Mockito.doThrow(new NotFoundException(String.format("Showplace with id %s not found", expectedComment.getShowplaceId())))
                    .when(showplaceValidator)
                    .validateShowplaceExistence(expectedComment.getShowplaceId());

            var exception = assertThrows(NotFoundException.class,
                    () -> commentService.getCommentsByShowplaceId(
                            pageRequest.getPageNumber(),
                            pageRequest.getPageSize(),
                            expectedComment.getShowplaceId()
                    )
            );

            assertThat(exception.getMessage())
                    .isEqualTo(String.format("Showplace with id %s not found", expectedComment.getShowplaceId()));
        }

        @Test
        @DisplayName("Успешное получение комментариев достопримечательности")
        public void testGetCommentsByShowplaceIdWithSuccessfulGetting(){
            when(commentRepository.findAllByShowplaceId(expectedComment.getShowplaceId(), pageRequest))
                    .thenReturn(new PageImpl<>(List.of(expectedComment)));

            var comments = commentService.getCommentsByShowplaceId(
                    pageRequest.getPageNumber(),
                    pageRequest.getPageSize(),
                    expectedComment.getShowplaceId()
            );

            InOrder inOrder = Mockito.inOrder(commentRepository, commentMapper, showplaceValidator);
            inOrder.verify(showplaceValidator, times(1))
                    .validateShowplaceExistence(expectedComment.getShowplaceId());
            inOrder.verify(commentRepository, times(1))
                    .findAllByShowplaceId(expectedComment.getShowplaceId(), pageRequest);
            inOrder.verify(commentMapper, times(1)).toDto(expectedComment);
            assertThat(comments.getContent()).hasOnlyElementsOfType(CommentDto.class);
            assertThat(comments.getContent()).hasSize(1);
            assertThat(comments.getContent().get(0)).isEqualTo(expectedCommentDto);
        }
    }

    @Nested
    class PostDesign{

        @Test
        @DisplayName("Попытка создать комментарий у несуществующей достопримечательности")
        public void testCreateCommentWithNotFoundShowplace(){
            Mockito.doThrow(new NotFoundException(String.format("Showplace with id %s not found", expectedComment.getShowplaceId())))
                    .when(showplaceValidator)
                    .validateShowplaceExistence(expectedComment.getShowplaceId());

            var exception = assertThrows(NotFoundException.class,
                    () -> commentService.createComment(expectedComment.getShowplaceId(), expectedComment.getContent()));

            assertThat(exception.getMessage())
                    .isEqualTo(String.format("Showplace with id %s not found", expectedComment.getShowplaceId()));
        }

        @Test
        @DisplayName("Успешное создание комментария")
        public void testCreateClassWithSuccessfulCreation(){
            when(commentRepository.save(expectedComment))
                    .thenReturn(expectedComment);

            CommentDto actualCommentDto = commentService.createComment(expectedComment.getShowplaceId(),
                    expectedComment.getContent());

            InOrder inOrder = Mockito.inOrder(commentRepository, showplaceValidator, commentMapper);
            inOrder.verify(showplaceValidator, times(1))
                    .validateShowplaceExistence(expectedComment.getShowplaceId());
            inOrder.verify(commentRepository, times(1)).save(expectedComment);
            inOrder.verify(commentMapper, times(1)).toDto(expectedComment);
            assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
        }
    }
}
