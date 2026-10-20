package com.app.backbiblioteca.book.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class CategorySetConverter implements AttributeConverter<Set<Category>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return null;
        }

        return categories.stream()
                .map(Category::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Category> convertToEntityAttribute(String value) {
        if (value == null || value.isBlank()) {
            return Collections.emptySet();
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .map(Category::valueOf)
                .collect(Collectors.toSet());
    }
}
