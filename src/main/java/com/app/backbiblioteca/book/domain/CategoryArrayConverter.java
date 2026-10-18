package com.app.backbiblioteca.book.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Converter
public class CategoryArrayConverter implements AttributeConverter<Category[], String> {

    @Override
    public String convertToDatabaseColumn(Category[] categories) {
        if (categories == null || categories.length == 0) {
            return null;
        }

        return Arrays.stream(categories)
                .map(Category::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Category[] convertToEntityAttribute(String value) {
        if (value == null || value.isBlank()) {
            return new Category[0];
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .map(Category::valueOf)
                .toArray(Category[]::new);
    }
}
