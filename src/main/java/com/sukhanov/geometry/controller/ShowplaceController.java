package com.sukhanov.geometry.controller;

import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.request.RequestModel;
import com.sukhanov.geometry.service.ShowplaceService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/showplace")
@RequiredArgsConstructor
public class ShowplaceController {

    private final ShowplaceService showplaceService;

    @GetMapping
    public Page<ShowplaceDto> findAllByRadius(@ParameterObject RequestModel requestModel){
        return showplaceService.findAllByRadius(requestModel);
    }

    @GetMapping("/city/{name}")
    public Page<ShowplaceDto> findAllByCity(@PathVariable(name = "name") String nameCity){
        return null;
    }
}
