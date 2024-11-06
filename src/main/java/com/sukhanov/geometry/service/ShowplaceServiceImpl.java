package com.sukhanov.geometry.service;

import com.sukhanov.geometry.mapper.ShowplaceMapper;
import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.request.RequestModel;
import com.sukhanov.geometry.model.sorting.SortedField;
import com.sukhanov.geometry.repository.ShowplaceRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ShowplaceServiceImpl implements ShowplaceService{

    private final ShowplaceRepository showplaceRepository;
    private final ShowplaceMapper showplaceMapper;

    @Override
    public Page<ShowplaceDto> findAllByRadius(@Valid RequestModel requestModel) {
        Map<SortedField, String> fields = IntStream.range(0,
                        Math.min(requestModel.getOrderBy().length, requestModel.getOrder().length)
                )
                    .boxed()
                    .collect(Collectors.toMap(i -> requestModel.getOrderBy()[i], i -> requestModel.getOrder()[i]));

        return showplaceRepository.findAllByRadius(
                        requestModel.getCategories(),
                        requestModel.getRating(),
                        fields,
                        requestModel.getLocation(),
                        requestModel.getRadius(),
                        QPageRequest.of(requestModel.getOffset(), requestModel.getLimit()))
                .map(showplaceMapper::toDto);
    }

    @Override
    public Page<ShowplaceDto> findAllByCity(RequestModel requestModel) {
        return null;
    }
}
