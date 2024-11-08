package com.sukhanov.geometry.controller.showplace;

import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.request.RequestModel;
import com.sukhanov.geometry.service.showplace.ShowplaceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/showplace")
@RequiredArgsConstructor
public class ShowplaceController {

    private final ShowplaceService showplaceService;

    @GetMapping
    public Page<ShowplaceDto> findAllByCriteria(@Valid @ParameterObject RequestModel requestModel){
        return showplaceService.findAllByCriteria(requestModel);
    }

    @PutMapping("/{uuid}/rating")
    public Double giveRating(@NotNull @PathVariable UUID uuid, @Min(0) @Max(5) @RequestBody Integer mark){
        return showplaceService.giveRating(uuid, mark);
    }

    @GetMapping("/{showplaceId}")
    public ShowplaceDto getShowplaceById(@NotNull UUID showplaceId){
        return showplaceService.getShowplaceById(showplaceId);
    }
}
