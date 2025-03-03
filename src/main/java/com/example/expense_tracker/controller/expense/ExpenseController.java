package com.example.expense_tracker.controller.expense;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense_tracker.dto.expense.ExpenseCreateDto;
import com.example.expense_tracker.dto.expense.ExpenseDto;
import com.example.expense_tracker.service.expense.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/expense")
@AllArgsConstructor
@Tag(name = "Gastos", description = "Operaciones relacionadas con los gastos")
@SecurityRequirement(name = "Security Token")
public class ExpenseController {

    private ExpenseService expenseService;

    @Operation(summary = "Crear un nuevo gasto", description = "Crea un gasto con nombre, descripción y el monto")
    @ApiResponse(responseCode = "201", description = "Gasto creado exitosamente")
    @PostMapping()
    public ResponseEntity<?> createExpense(@RequestBody @Valid ExpenseCreateDto expenseCreateDto){
        ExpenseDto expenseDto=expenseService.createExpense(expenseCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseDto);
    }

    @Operation(summary = "Eliminar un gasto", description = "Elimina un gasto específico por su ID")
    @ApiResponse(responseCode = "204", description = "Gasto eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Gasto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@Parameter(description = "ID del gasto a eliminar") @PathVariable("id") Long id_expense){
        expenseService.deleteExpense(id_expense);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Obtener todos los gastos", description = "Devuelve una lista de todos los gastos existentes")
    @ApiResponse(responseCode = "200", description = "Lista de gastos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<?> getAllExpenses(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int limit,
                                            @RequestParam(required = false) String categoryName) {
    Page<ExpenseDto> expensesPage = expenseService.getAllExpenses(page, limit, categoryName);
    return ResponseEntity.ok().body(Map.of(
            "data", expensesPage.getContent(),
            "page", page,
            "limit", limit,
            "total", expensesPage.getTotalElements()));
    }

    @Operation(summary = "Actualizar un gasto", description = "Actualiza el contenido de un gasto existente por ID")
    @ApiResponse(responseCode = "200", description = "Gasto actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Gasto no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@Parameter(description = "ID del gasto a actualizar") @PathVariable("id") Long id_expense, 
                                            @RequestBody @Valid ExpenseCreateDto expenseCreateDto){
        ExpenseDto expenseUpdated=expenseService.updateExpense(id_expense, expenseCreateDto);
        return ResponseEntity.ok().body(expenseUpdated);
    }

    @Operation(summary = "Resumen general de gastos", description = "Obtiene un resumen general de los gastos, incluyendo estadísticas y totales.")
    @ApiResponse(responseCode = "200", description = "Resumen de gastos obtenido exitosamente")
    @GetMapping("/sumary")
    public ResponseEntity<?> sumaryExpenses(){
        Map<String, Object> sumary=expenseService.getExpensesSummary();
        return ResponseEntity.ok().body(sumary);
    }

    @Operation(summary = "Resumen mensual de gastos", description = "Obtiene un resumen de los gastos correspondientes a un mes específico.")
    @ApiResponse(responseCode = "200", description = "Resumen mensual de gastos obtenido exitosamente")
    @ApiResponse(responseCode = "400", description = "Mes inválido")
    @GetMapping("/sumary/{month}")
    public ResponseEntity<?> getMonthlyExpensesSummary(@Parameter(description = "Número del mes (1-12) para obtener el resumen de gastos", example = "5") @PathVariable("month") int month) {
        Map<String, Object> summary = expenseService.getMonthlyExpensesSummary(month);
        return ResponseEntity.ok().body(summary);
    }


}
