package com.example.expense_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expense_tracker.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findCategoryByName(String name);
}
