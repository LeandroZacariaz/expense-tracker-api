package com.example.expense_tracker.mappers.user;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.expense_tracker.domain.Expense;
import com.example.expense_tracker.domain.User;
import com.example.expense_tracker.dto.expense.ExpenseDto;
import com.example.expense_tracker.dto.user.UserDto;
import com.example.expense_tracker.dto.user.UserRegisterDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id_user", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User userRegisterDtoToUser(UserRegisterDto userRegisterDto);

    @Mapping(target = "expenses", expression = "java(expenseToExpenseDtos(user.getExpenses()))")
    UserDto UserToUserDto(User user);

    default List<ExpenseDto> expenseToExpenseDtos(List<Expense> expenses) {
        return expenses == null ? null : expenses.stream()
                .map(expense -> new ExpenseDto(expense.getId_expense(), expense.getName(), expense.getDescription(), expense.getAmount()))
                .collect(Collectors.toList());
    }
}
