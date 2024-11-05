package com.sukhanov.geometry.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "showplace")
public class Showplace {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "city", length = 64, nullable = false)
    private String city;

    @Column(name = "description", length = 2048, nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "location", columnDefinition = "geometry(Point,4326)")
    private Point location;

    @Embedded
    private Rating rating;

    @PrePersist
    public void setDefaultRatingValues(){
        if(rating == null){
            rating = new Rating(0, 0);
        }
    }
}
