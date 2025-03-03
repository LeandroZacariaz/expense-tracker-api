package com.example.expense_tracker.mappers.category;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.expense_tracker.domain.Category;
import com.example.expense_tracker.dto.category.CategoryCreateDto;
import com.example.expense_tracker.dto.category.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id_category", ignore = true)
    Category categoryCreateDtoToCategory(CategoryCreateDto categoryCreateDto);

    CategoryDto categoryToCategoryDto(Category category);
}
