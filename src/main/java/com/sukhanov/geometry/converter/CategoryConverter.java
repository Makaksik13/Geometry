package com.sukhanov.geometry.converter;

import com.sukhanov.geometry.model.entity.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Category category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }

    @Override
    public Category convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Category.values())
                .filter(c -> Objects.equals(c.getCode(), code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
