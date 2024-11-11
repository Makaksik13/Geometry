package com.sukhanov.geometry.mapper;

import com.sukhanov.geometry.model.dto.LocationPoint;
import com.sukhanov.geometry.model.dto.ShowplaceDto;
import com.sukhanov.geometry.model.entity.Showplace;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = RatingMapper.class
)
public interface ShowplaceMapper {

    @Mapping(target = "location", qualifiedByName = "makeLocation", source = "location")
    ShowplaceDto toDto(Showplace showplace);

    @Mapping(target = "location", qualifiedByName = "makePoint", source = "location")
    Showplace toEntity(ShowplaceDto showplaceDto);

    @Named("makePoint")
    default Point makePoint(LocationPoint pointDto){
        if(pointDto == null) return null;
        GeometryFactory geometryFactory = new GeometryFactory();

        Coordinate coordinate = new Coordinate(pointDto.getLongitudeX(), pointDto.getLatitudeY());
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326);
        return point;
    }

    @Named("makeLocation")
    default LocationPoint makeLocation(Point point){
        if(point == null) return null;
        return new LocationPoint(point.getX(), point.getY());
    }
}
