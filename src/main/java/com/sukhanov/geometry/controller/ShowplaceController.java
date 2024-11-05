package com.sukhanov.geometry.controller;

import com.sukhanov.geometry.model.dto.LocationPoint;
import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.entity.Category;
import com.sukhanov.geometry.model.sorting.SortedField;
import com.sukhanov.geometry.service.ShowplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShowplaceController {

    private final ShowplaceService showplaceService;

    @GetMapping
    public Page<ShowplaceDto> findAllByRadius(
            @RequestParam(value = "longitudeX") Double x,
            @RequestParam(value = "latitudeY") Double y,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(value = "orderBy", required = false, defaultValue = "DISTANCE") SortedField[] orderBy,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String[] order,
            @RequestParam(value = "radius", required = false, defaultValue = "10") Double radius,
            @RequestParam(value = "categories", required = false) List<Category> categories,
            @RequestParam(value = "hasMoraRating", required = false, defaultValue = "0") Double rating
    ){
        return showplaceService.findAllByRadius(
                categories,
                rating,
                orderBy,
                order,
                radius,
                new LocationPoint(x, y),
                QPageRequest.of(offset, limit)
        );
    }
}
