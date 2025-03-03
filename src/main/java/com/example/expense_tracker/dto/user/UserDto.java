package com.example.expense_tracker.dto.user;

import java.util.List;

import com.example.expense_tracker.domain.enums.RoleEnumUser;
import com.example.expense_tracker.dto.expense.ExpenseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un usuario en el sistema")
public record UserDto(
    @Schema(description = "ID único del usuario", example = "10")
    Long id_user,

    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    String name,

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    String email,

    @Schema(description = "Rol del usuario en el sistema", example = "USER")
    RoleEnumUser role,

    @Schema(description = "Lista de gastos asociadas al usuario")
    List<ExpenseDto> expenses
) {

}
