package com.sukhanov.geometry.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    SPORT(100), MUSIC(200), TECHNOLOGY(300);

    private final Integer code;
}
