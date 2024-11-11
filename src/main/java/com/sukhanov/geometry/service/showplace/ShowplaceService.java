package com.sukhanov.geometry.service.showplace;

import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.request.RequestModel;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ShowplaceService {

    Page<ShowplaceDto> getAllByCriteria(RequestModel requestModel);

    Double giveRating(UUID uuid, Integer mark);

    ShowplaceDto getShowplaceById(UUID uuid);

    ShowplaceDto createShowplace(ShowplaceDto showplaceDto);
}

