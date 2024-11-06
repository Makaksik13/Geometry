package com.sukhanov.geometry.service;

import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.request.RequestModel;
import org.springframework.data.domain.Page;

public interface ShowplaceService {

    Page<ShowplaceDto> findAllByRadius(RequestModel requestModel);

    Page<ShowplaceDto> findAllByCity(RequestModel requestModel);
}

