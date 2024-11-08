package com.sukhanov.geometry.repository;

import com.sukhanov.geometry.model.entity.Showplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShowplaceRepository extends JpaRepository<Showplace, UUID>, QuerydslPredicateExecutor<Showplace> {
}
