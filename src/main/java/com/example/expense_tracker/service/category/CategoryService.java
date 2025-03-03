package com.example.expense_tracker.service.category;

import java.util.List;

import com.example.expense_tracker.domain.Category;
import com.example.expense_tracker.dto.category.CategoryCreateDto;
import com.example.expense_tracker.dto.category.CategoryDto;

public interface CategoryService {
    CategoryDto createCategory(CategoryCreateDto categoryCreateDto);

    void deleteCategory(Long id_category);

    Category getCategoryById(Long id_category);

    Category getCategoryByName(String name);

    List<CategoryDto> getAllCategory();
}
