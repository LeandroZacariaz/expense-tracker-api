package com.example.expense_tracker.dto.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "DTO para la creación de un gasto")
public record ExpenseCreateDto(
    @NotNull(message = "The expense name cannot be null.")
    @NotBlank(message = "The expense name cannot be empty.")
    @Schema(description = "Nombre del gasto", example = "Compra de material de oficina")
    String name,
    @NotNull(message = "The expense description cannot be null.")
    @NotBlank(message = "The expense description cannot be empty.")
    @Schema(description = "Descripción del gasto", example = "Compra de bolígrafos, papel y carpetas para la oficina")
    String description,
    @NotNull(message = "The expense amount cannot be null.")
    @Positive(message = "The expense amount must be positive.")
    @Schema(description = "Monto del gasto", example = "150.75")
    Double amount,
    @NotNull(message = "The category name cannot be null.")
    @NotBlank(message = "The category name cannot be empty.")
    @Schema(description = "Nombre de la categoría del gasto", example = "Oficina")
    String name_category
) {

}
