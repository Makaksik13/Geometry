package com.sukhanov.geometry.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Rating {

    @Column(name = "total_sum")
    private long totalSum ;

    @Column(name = "amount")
    private long amount;
}
