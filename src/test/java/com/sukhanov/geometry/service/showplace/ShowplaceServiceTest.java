package com.sukhanov.geometry.service.showplace;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sukhanov.geometry.exception.NotFoundException;
import com.sukhanov.geometry.mapper.RatingMapperImpl;
import com.sukhanov.geometry.mapper.ShowplaceMapper;
import com.sukhanov.geometry.mapper.ShowplaceMapperImpl;
import com.sukhanov.geometry.model.dto.RatingDto;
import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.entity.Category;
import com.sukhanov.geometry.model.entity.Field;
import com.sukhanov.geometry.model.entity.Rating;
import com.sukhanov.geometry.model.entity.Showplace;
import com.sukhanov.geometry.model.request.RequestModel;
import com.sukhanov.geometry.repository.ShowplaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.querydsl.QPageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование ShowplaceService")
@ExtendWith(MockitoExtension.class)
public class ShowplaceServiceTest {

    @InjectMocks
    private ShowplaceServiceImpl showplaceService;

    @Mock
    private ShowplaceRepository showplaceRepository;
    @Spy
    private ShowplaceMapper showplaceMapper = new ShowplaceMapperImpl(new RatingMapperImpl());

    private Showplace expectedShowplace;
    private ShowplaceDto expectedShowplaceDto;
    private Integer mark;
    private RequestModel requestModel;

    @BeforeEach
    public void init(){
        expectedShowplace = Showplace.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .city("City")
                .category(Category.SPORT)
                .description("Description")
                .rating(new Rating(0, 0))
                .build();

        expectedShowplaceDto = ShowplaceDto.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .city("City")
                .category(Category.SPORT)
                .rating(new RatingDto(0, 0, 0.0))
                .description("Description")
                .build();

        requestModel = RequestModel.builder()
                .order(new String[]{})
                .orderBy(new Field[]{})
                .categories(Collections.emptyList())
                .limit(1)
                .offset(1)
                .build();

        mark = 5;
    }

    @Nested
    class PostDesign{

        @Test
        @DisplayName("Успешное создание достопримечательности")
        public void testCreateShowplaceWithSuccessfulCreation(){
            when(showplaceRepository.save(expectedShowplace))
                    .thenReturn(expectedShowplace);

            ShowplaceDto actualShowplaceDto = showplaceService.createShowplace(expectedShowplaceDto);

            InOrder inOrder = Mockito.inOrder(showplaceRepository, showplaceMapper);
            inOrder.verify(showplaceMapper, times(1)).toEntity(expectedShowplaceDto);
            inOrder.verify(showplaceRepository, times(1)).save(expectedShowplace);
            inOrder.verify(showplaceMapper, times(1)).toDto(expectedShowplace);
            assertThat(actualShowplaceDto).isEqualTo(expectedShowplaceDto);
        }
    }

    @Nested
    class GetDesign{

        @Test
        @DisplayName("Попытка получить несуществующую достопримечательность")
        public void testGetShowplaceByIdWithNotFoundShowplace(){
            when(showplaceRepository.findById(expectedShowplace.getId()))
                    .thenReturn(Optional.empty());

            var exception = assertThrows(NotFoundException.class,
                    () -> showplaceService.getShowplaceById(expectedShowplace.getId()));

            assertThat(exception.getMessage())
                    .isEqualTo(String.format("Showplace with id %s not found", expectedShowplace.getId()));
        }

        @Test
        @DisplayName("Успешное получение достопримечательности")
        public void testGetShowplaceByIdWithSuccessfulGetting(){
            when(showplaceRepository.findById(expectedShowplace.getId()))
                    .thenReturn(Optional.of(expectedShowplace));

            ShowplaceDto actualShowplaceDto = showplaceService.getShowplaceById(expectedShowplace.getId());

            InOrder inOrder = Mockito.inOrder(showplaceRepository, showplaceMapper);
            inOrder.verify(showplaceRepository, times(1)).findById(expectedShowplace.getId());
            inOrder.verify(showplaceMapper, times(1)).toDto(expectedShowplace);
            assertThat(actualShowplaceDto).isEqualTo(expectedShowplaceDto);
        }

        @Test
        @DisplayName("Успешное получение достопримечательностей по фильтрам в определённом порядке")
        public void testFindAllByCriteriaWithSuccessfulGetting(){
            QPageRequest pageRequest = QPageRequest.of(requestModel.getOffset(), requestModel.getLimit());
            when(showplaceRepository.findAll(any(BooleanExpression.class), eq(pageRequest)))
                    .thenReturn(new PageImpl<>(List.of(expectedShowplace)));

            var actualShowplacesDto = showplaceService.getAllByCriteria(requestModel).getContent();

            InOrder inOrder = Mockito.inOrder(showplaceRepository, showplaceMapper);
            inOrder.verify(showplaceRepository, times(1))
                    .findAll(any(BooleanExpression.class), eq(pageRequest));
            inOrder.verify(showplaceMapper, times(1)).toDto(expectedShowplace);
            assertThat(actualShowplacesDto).hasOnlyElementsOfType(ShowplaceDto.class);
            assertThat(actualShowplacesDto).hasSize(1);
            assertThat(actualShowplacesDto.get(0)).isEqualTo(expectedShowplaceDto);
        }
    }

    @Nested
    class PutDesign{

        @Test
        @DisplayName("Попытка поставить оценку несуществующей достопримечательности")
        public void testGiveRatingWithNotFoundShowplace(){
            when(showplaceRepository.findById(expectedShowplace.getId()))
                    .thenReturn(Optional.empty());

            var exception = assertThrows(NotFoundException.class,
                    () -> showplaceService.giveRating(expectedShowplace.getId(), 5));

            assertThat(exception.getMessage())
                    .isEqualTo(String.format("Showplace with id %s not found", expectedShowplace.getId()));
        }

        @Test
        @DisplayName("Успешное выставление оценки достопримечательности")
        public void testGiveRatingWithSuccessfulGetting(){
            when(showplaceRepository.findById(expectedShowplace.getId()))
                    .thenReturn(Optional.of(expectedShowplace));

            Double actualRating = showplaceService.giveRating(expectedShowplace.getId(), mark);

            InOrder inOrder = Mockito.inOrder(showplaceRepository, showplaceMapper);
            inOrder.verify(showplaceRepository, times(1)).findById(expectedShowplace.getId());
            inOrder.verify(showplaceRepository, times(1)).save(expectedShowplace);
            assertThat(actualRating).isBetween(0.0, 5.0);
            assertThat(actualRating).isEqualTo(Double.valueOf(mark));
        }
    }
}
