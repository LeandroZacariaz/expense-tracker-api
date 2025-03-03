package com.example.expense_tracker.dto.errors;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para errores genéricos con detalles adicionales")
public record ErrorGenericDto(
    @Schema(description = "Mensaje principal del error", example = "Ocurrió un error de validación")
    String message,
    
    @Schema(description = "Lista de errores detallados en formato clave-valor")
    List<Map<String, String>> errors
) {

}