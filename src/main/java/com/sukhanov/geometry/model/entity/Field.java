package com.sukhanov.geometry.model.entity;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sukhanov.geometry.model.dto.LocationPoint;
import com.sukhanov.geometry.model.request.RequestModel;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.querydsl.QSort;

import static com.sukhanov.geometry.model.entity.QShowplace.showplace;

public enum Field {

    RATING {
        @Override
        public BooleanExpression getFilterExpression(RequestModel requestModel) {
            if(requestModel.getRating() == null){
                return Expressions.TRUE;
            }
            return Expressions.cases().
                            when(showplace.rating.amount.eq(0L))
                    .then(0.0)
                    .otherwise(showplace.rating.totalSum.doubleValue().divide(showplace.rating.amount))
                    .goe(requestModel.getRating());
        }

        @Override
        public QSort getSort(RequestModel requestModel, Order order) {
            if(requestModel.getRating() == null){
                return QSort.unsorted();
            }

            return new QSort(new OrderSpecifier<>(order, Expressions.cases().
                            when(showplace.rating.amount.eq(0L))
                    .then(0.0)
                    .otherwise(showplace.rating.totalSum.doubleValue().divide(showplace.rating.amount)))
            );
        }
    },

    DISTANCE {
        @Override
        public BooleanExpression getFilterExpression(RequestModel requestModel) {
            LocationPoint location = requestModel.getLocation();
            if(requestModel.getRadius() != null || location == null ||
                    location.getLatitudeY() == null || location.getLongitudeX() == null){
                return Expressions.TRUE;
            }

            return showplace.location.distance(makePoint(requestModel.getLocation())).
                        loe(requestModel.getRadius());
        }

        @Override
        public QSort getSort(RequestModel requestModel, Order order) {
            LocationPoint location = requestModel.getLocation();
            if(requestModel.getRadius() != null || location == null ||
                    location.getLatitudeY() == null || location.getLongitudeX() == null){
                return QSort.unsorted();
            }
            return new QSort(new OrderSpecifier(order, showplace.location.distance(
                    makePoint(requestModel.getLocation())))
            );
        }
    },

    CATEGORY {
        @Override
        public BooleanExpression getFilterExpression(RequestModel requestModel) {
            return (requestModel.getCategories() == null || requestModel.getCategories().isEmpty()) ?
                    Expressions.TRUE :
                    showplace.category.in(requestModel.getCategories());
        }

        @Override
        public QSort getSort(RequestModel requestModel, Order order) {
            return new QSort(new OrderSpecifier<>(order, showplace.category));
        }
    },

    CITY {
        @Override
        public BooleanExpression getFilterExpression(RequestModel requestModel) {
            String city = requestModel.getCity();
            return city == null || city.isBlank() ?
                    Expressions.TRUE :
                    showplace.city.equalsIgnoreCase(city);
        }

        @Override
        public QSort getSort(RequestModel requestModel, Order order) {
            return new QSort(new OrderSpecifier<>(order, showplace.city));
        }
    };

    public abstract BooleanExpression getFilterExpression(RequestModel requestModel);
    public abstract QSort getSort(RequestModel requestModel, Order order);

    private static Point makePoint(LocationPoint pointDto){
        if(pointDto == null) return null;
        GeometryFactory geometryFactory = new GeometryFactory();

        Coordinate coordinate = new Coordinate(pointDto.getLongitudeX(), pointDto.getLatitudeY());
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326);
        return point;
    }
}
