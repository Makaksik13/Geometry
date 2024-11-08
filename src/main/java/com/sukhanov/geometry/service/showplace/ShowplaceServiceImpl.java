package com.sukhanov.geometry.service.showplace;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sukhanov.geometry.exception.NotFoundException;
import com.sukhanov.geometry.mapper.ShowplaceMapper;
import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.entity.Rating;
import com.sukhanov.geometry.model.entity.Showplace;
import com.sukhanov.geometry.model.request.RequestModel;
import com.sukhanov.geometry.model.entity.Field;
import com.sukhanov.geometry.repository.ShowplaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShowplaceServiceImpl implements ShowplaceService {

    private final ShowplaceRepository showplaceRepository;
    private final ShowplaceMapper showplaceMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ShowplaceDto> findAllByCriteria(RequestModel requestModel) {
        QSort qSort = makeSort(requestModel);
        QPageRequest pageRequest = QPageRequest.of(requestModel.getOffset(), requestModel.getLimit(), qSort);

        return showplaceRepository.findAll(makeFilterExpression(requestModel), pageRequest)
                .map(showplaceMapper::toDto);
    }

    @Override
    @Transactional
    public Double giveRating(UUID uuid, Integer mark){
        Showplace showplace = getById(uuid);

        Rating rating = showplace.getRating();
        long newSum = rating.getTotalSum() + mark;
        long newAmount = rating.getAmount() + 1;
        rating.setTotalSum(newSum);
        rating.setAmount(newAmount);
        showplaceRepository.save(showplace);

        return Math.round((double) newSum / newAmount * 100) / 100.0;
    }

    @Override
    @Transactional(readOnly = true)
    public ShowplaceDto getShowplaceById(UUID uuid){
        return showplaceMapper.toDto(getById(uuid));
    }

    private QSort makeSort(RequestModel requestModel){
        QSort qSort = new QSort();
        String[] orders = requestModel.getOrder();
        for (int i = 0; i < requestModel.getOrderBy().length; i++) {
            qSort = qSort.and(requestModel.getOrderBy()[i].getSort(requestModel,
                    orders.length <= i || orders[i].startsWith("A") || orders[i].startsWith("a") ?
                            Order.ASC :
                            Order.DESC
            ));
        }
        return qSort;
    }

    private BooleanExpression makeFilterExpression(RequestModel requestModel){
        BooleanExpression filterExpression = Expressions.TRUE;
        for(Field field: Field.values()){
            filterExpression = filterExpression.and(field.getFilterExpression(requestModel));
        }
        return filterExpression;
    }

    private Showplace getById(UUID uuid){
        return showplaceRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException(String.format("Showplace with id %s not found", uuid)));
    }
}
