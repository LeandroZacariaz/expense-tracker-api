package com.example.expense_tracker.service.expense;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.expense_tracker.domain.Category;
import com.example.expense_tracker.domain.Expense;
import com.example.expense_tracker.domain.User;
import com.example.expense_tracker.dto.expense.ExpenseCreateDto;
import com.example.expense_tracker.dto.expense.ExpenseDto;
import com.example.expense_tracker.exceptions.ResourceNotFoundException;
import com.example.expense_tracker.mappers.expense.ExpenseMapper;
import com.example.expense_tracker.repository.ExpenseRepository;
import com.example.expense_tracker.service.auth.AuthService;
import com.example.expense_tracker.service.category.CategoryService;
import com.example.expense_tracker.service.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{
    private UserService userService;
    private ExpenseRepository expenseRepository;
    private ExpenseMapper expenseMapper;
    private CategoryService categoryService;
    private AuthService authService;

    @Override
    public ExpenseDto createExpense(ExpenseCreateDto expenseCreateDto) {
        Expense expenseCreated=expenseMapper.expenseCreateDtoToExpense(expenseCreateDto);
        expenseCreated.setDate(LocalDate.now());
        User userLogged=userService.getLoggingInUser();
        Category category=categoryService.getCategoryByName(expenseCreateDto.name_category());
        expenseCreated.setCategory(category);
        expenseCreated.setUser(userLogged);
        return expenseMapper.expenseToExpenseDto(expenseRepository.save(expenseCreated));
    }

    @Override
    public void deleteExpense(Long id_expense){
        Expense expenseDelete=expenseRepository.findById(id_expense)
                                .orElseThrow(()-> new ResourceNotFoundException("The expense with id: "+id_expense+"does not exist."));
        String currentEmailLogger=authService.getCurrentUserEmail();
        if(expenseDelete.getUser().getEmail().equals(currentEmailLogger)){
            expenseRepository.delete(expenseDelete);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }                   
    }

    @Override
    public ExpenseDto getExpenseById(Long id_expense){
        Expense expense=expenseRepository.findById(id_expense)
                                .orElseThrow(()-> new ResourceNotFoundException("The expense with id: "+id_expense+"does not exist."));
        String currentEmailLogger=authService.getCurrentUserEmail();
        if(expense.getUser().getEmail().equals(currentEmailLogger)){
            return expenseMapper.expenseToExpenseDto(expense);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }
    }

    @Override
    public Page<ExpenseDto> getAllExpenses(int page, int limit, String categoryName) {
    String currentUserEmail = authService.getCurrentUserEmail();
    Pageable pageable = PageRequest.of(page - 1, limit);
    
    if (categoryName != null && !categoryName.isEmpty()) {
        return expenseRepository.findByUser_EmailAndCategory_Name(currentUserEmail, categoryName, pageable)
                .map(expense -> expenseMapper.expenseToExpenseDto(expense));
    }
    
    return expenseRepository.findByUser_Email(currentUserEmail, pageable)
            .map(expense -> expenseMapper.expenseToExpenseDto(expense));
    }

    @Override
    public ExpenseDto updateExpense(Long id_expense, ExpenseCreateDto expenseCreateDto) {
        Expense expenseUpdate=expenseRepository.findById(id_expense)
                                    .orElseThrow(()-> new ResourceNotFoundException("The expense with id: "+id_expense+"does not exist."));
        String currentUserEmail=authService.getCurrentUserEmail();
        if (!expenseUpdate.getUser().getEmail().equals(currentUserEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }else{
            expenseUpdate.setName(expenseCreateDto.name());
            expenseUpdate.setDescription(expenseCreateDto.description());
            expenseUpdate.setAmount(expenseCreateDto.amount());
            expenseUpdate.setCategory(categoryService.getCategoryByName(expenseCreateDto.name_category()));
            return expenseMapper.expenseToExpenseDto(expenseRepository.save(expenseUpdate));
        }
    }
    
    @Override
    public Map<String, Object> getExpensesSummary() {
        String currentEmailLogged=authService.getCurrentUserEmail();

        List<Expense> allExpenses = expenseRepository.findByUser_Email(currentEmailLogged);

        double totalAmount = allExpenses.stream().mapToDouble(Expense::getAmount).sum();

        Map<String, Double> expensesByCategory = allExpenses.stream()
                .collect(Collectors.groupingBy(
                    expense -> expense.getCategory().getName(),
                    Collectors.summingDouble(Expense::getAmount)
                ));

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalAmount", totalAmount);
        summary.put("expensesByCategory", expensesByCategory);
        summary.put("totalExpenses", allExpenses.size());

        return summary;
    }

    @Override
    public Map<String, Object> getMonthlyExpensesSummary(int month) {
        Year currentYear = Year.now();
        System.out.println("Usuario authenticado: " + authService.getCurrentUserEmail());
        String currentEmailLogged=authService.getCurrentUserEmail();
        List<Expense> monthlyExpenses = expenseRepository.findByUser_Email(currentEmailLogged).stream()
                .filter(expense -> {
                    LocalDate expenseDate = expense.getDate();
                    return expenseDate.getMonthValue() == month && expenseDate.getYear() == currentYear.getValue();
                })
                .collect(Collectors.toList());

        double totalAmount = monthlyExpenses.stream().mapToDouble(Expense::getAmount).sum();

        Map<String, Double> expensesByCategory = monthlyExpenses.stream()
                .collect(Collectors.groupingBy(
                    expense -> expense.getCategory().getName(),
                    Collectors.summingDouble(Expense::getAmount)
                ));

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalAmount", totalAmount);
        summary.put("expensesByCategory", expensesByCategory);
        summary.put("totalExpenses", monthlyExpenses.size());

        return summary;
    }



}
