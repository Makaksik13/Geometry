package com.sukhanov.geometry.service;

import com.sukhanov.geometry.model.dto.LocationPoint;
import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.entity.Category;
import com.sukhanov.geometry.model.sorting.SortedField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;

import java.util.List;

public interface ShowplaceService {

    Page<ShowplaceDto> findAllByRadius(
            List<Category> categories,
            Double rating,
            SortedField[] orderBy,
            String[] order,
            Double radius,
            LocationPoint locationPoint,
            QPageRequest pageRequest
    );
}

