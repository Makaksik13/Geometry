package com.sukhanov.geometry.validation.Validator.showplace;

import com.sukhanov.geometry.exception.NotFoundException;
import com.sukhanov.geometry.repository.ShowplaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShowplaceValidatorImpl implements ShowplaceValidator{

    private final ShowplaceRepository showplaceRepository;

    @Override
    public void validateShowplaceExistence(UUID showplaceId){
        showplaceRepository.findById(showplaceId)
                .orElseThrow(() -> new NotFoundException(String.format("Showplace with id %s not found", showplaceId)));
    }
}
