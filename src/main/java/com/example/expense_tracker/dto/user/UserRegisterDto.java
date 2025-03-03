package com.example.expense_tracker.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Estructura para registrar un nuevo usuario")
public record UserRegisterDto(
    @NotNull(message = "The user name cannot be null.")
    @NotBlank(message = "The user name cannot be empty.")
    @Schema(description = "Nombre del usuario", example = "Ana López")
    String name,
    @NotNull(message = "The user email cannot be null.")
    @NotBlank(message = "The user email cannot be empty.")
    @Email(message = "The user email must be a valid email address.")
    @Schema(description = "Correo electrónico del usuario", example = "ana.lopez@email.com")
    String email,
    @NotNull(message = "The user password cannot be null.")
    @NotBlank(message = "The user password cannot be empty.")
    @Schema(description = "Contraseña del usuario", example = "securePass123")
    String password
) {

}

