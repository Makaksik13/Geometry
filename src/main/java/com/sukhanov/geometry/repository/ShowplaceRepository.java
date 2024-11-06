package com.sukhanov.geometry.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.sukhanov.geometry.model.dto.LocationPoint;
import com.sukhanov.geometry.model.entity.Category;
import com.sukhanov.geometry.model.entity.QShowplace;
import com.sukhanov.geometry.model.entity.Showplace;
import com.sukhanov.geometry.model.sorting.SortedField;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface ShowplaceRepository extends JpaRepository<Showplace, UUID>, QuerydslPredicateExecutor<Showplace>{

    Page<Showplace> findAllByCity(String nameCity, Pageable pageable);

    default Page<Showplace> findAllByRadius(
            List<Category> categories,
            Double rating,
            Map<SortedField, String> fields,
            LocationPoint locationPoint,
            Double radius,
            QPageRequest pageRequest
    ){
        QShowplace showplace = QShowplace.showplace;
        QSort qSort = new QSort();

        BooleanExpression showplaceHasCategories = (categories == null || categories.isEmpty()) ?
                Expressions.TRUE :
                showplace.category.in(categories);

        NumberExpression<Double> calculatedRating = Expressions.cases().
                        when(showplace.rating.amount.eq(0L))
                .then(0.0)
                .otherwise(showplace.rating.totalSum.doubleValue().divide(showplace.rating.amount));
        if(fields.containsKey(SortedField.RATING))
            qSort = addSort(qSort, calculatedRating, fields.get(SortedField.RATING));

        NumberExpression<Double> calculatedDistance = showplace.location.distance(makePoint(locationPoint));
        if(fields.containsKey(SortedField.DISTANCE))
            qSort = addSort(qSort, calculatedDistance, fields.get(SortedField.DISTANCE));

        pageRequest = pageRequest.withSort(qSort);

        var qw = calculatedDistance.loe(radius)
                .and(calculatedRating.goe(rating))
                .and(showplaceHasCategories);
        return this.findAll(qw,
                pageRequest);
    }

    private Point makePoint(LocationPoint pointDto){
            if(pointDto == null) return null;
            GeometryFactory geometryFactory = new GeometryFactory();

            Coordinate coordinate = new Coordinate(pointDto.getLongitudeX(), pointDto.getLatitudeY());
            Point point = geometryFactory.createPoint(coordinate);
            point.setSRID(4326);
            return point;
    }

    private QSort addSort(QSort qSort, NumberExpression expression, String order){
        if(order.charAt(0) == 'a' || order.charAt(0) == 'A'){
            return qSort.and(expression.asc());
        }
        return qSort.and(expression.desc());
    }
}
