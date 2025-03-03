package com.example.expense_tracker.service.expense;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.expense_tracker.dto.expense.ExpenseCreateDto;
import com.example.expense_tracker.dto.expense.ExpenseDto;


public interface ExpenseService {
    ExpenseDto createExpense(ExpenseCreateDto expenseCreateDto);
    void deleteExpense(Long id_expense);
    ExpenseDto getExpenseById(Long id_expense);
    Page<ExpenseDto> getAllExpenses(int page, int limit, String categoryName);
    ExpenseDto updateExpense(Long id_expense, ExpenseCreateDto expenseCreateDto);
    Map<String, Object> getExpensesSummary();
    Map<String, Object> getMonthlyExpensesSummary(int month);
}
