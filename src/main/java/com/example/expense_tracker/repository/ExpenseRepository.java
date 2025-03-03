package com.example.expense_tracker.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expense_tracker.domain.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

    Page<Expense> findByUser_EmailAndCategory_Name(String currentUserEmail, String categoryName, Pageable pageable);

    Page<Expense> findByUser_Email(String currentUserEmail, Pageable pageable);

    List<Expense> findByUser_Email(String currentUserEmail);

}
