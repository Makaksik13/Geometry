package com.sukhanov.geometry.service;

import com.sukhanov.geometry.mapper.ShowplaceMapper;
import com.sukhanov.geometry.model.dto.LocationPoint;
import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.entity.Category;
import com.sukhanov.geometry.model.sorting.SortedField;
import com.sukhanov.geometry.repository.ShowplaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ShowplaceServiceImpl implements ShowplaceService{

    private final ShowplaceRepository showplaceRepository;
    private final ShowplaceMapper showplaceMapper;

    @Override
    public Page<ShowplaceDto> findAllByRadius(
            List<Category> categories,
            Double rating,
            SortedField[] orderBy,
            String[] order,
            Double radius,
            LocationPoint locationPoint,
            QPageRequest pageRequest
    ) {
        Map<SortedField, String> fields;
        if (order != null && orderBy != null){
            fields = IntStream.range(0, Math.min(orderBy.length, order.length))
                    .boxed()
                    .collect(Collectors.toMap(i -> orderBy[i], i -> order[i]));
        }else {
            fields = new HashMap<>();
        }

        return showplaceRepository.findAllByRadius(categories, rating, fields, locationPoint, radius, pageRequest)
                .map(showplaceMapper::toDto);
    }

}
