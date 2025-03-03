package com.example.expense_tracker.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense_tracker.dto.auth.AuthResponseDto;
import com.example.expense_tracker.dto.user.UserLoginDto;
import com.example.expense_tracker.dto.user.UserRegisterDto;
import com.example.expense_tracker.service.auth.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Authentication", description = "Endpoints para autenticación de usuarios")
public class AuthController {
    private AuthService authService;

    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario autenticarse con email y contraseña")
    @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid UserLoginDto userLoginDto){
        return ResponseEntity.ok(authService.login(userLoginDto));
    }

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea una nueva cuenta de usuario")
    @ApiResponse(responseCode = "200", description = "Registro exitoso")
    @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existente")
    @PostMapping("register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid UserRegisterDto userRegisterDto){
        return ResponseEntity.ok(authService.register(userRegisterDto));
    }
}
