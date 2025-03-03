package com.example.expense_tracker.dto.expense;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para representar un gasto sin usar la entidad directamente")
public record ExpenseDto(
    @Schema(description = "Identificador único del gasto", example = "1")
    Long id_expense,
    @Schema(description = "Nombre del gasto", example = "Compra de material de oficina")
    String name,
    @Schema(description = "Descripción del gasto", example = "Compra de bolígrafos, papel y carpetas para la oficina")
    String description,
    @Schema(description = "Monto del gasto", example = "150.75")
    Double amount
) {

}
