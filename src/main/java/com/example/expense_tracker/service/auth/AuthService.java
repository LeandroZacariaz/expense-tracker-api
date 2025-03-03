package com.example.expense_tracker.service.auth;

import com.example.expense_tracker.dto.auth.AuthResponseDto;
import com.example.expense_tracker.dto.user.UserLoginDto;
import com.example.expense_tracker.dto.user.UserRegisterDto;

public interface AuthService {
    AuthResponseDto login(UserLoginDto userLoginDto);
    AuthResponseDto register(UserRegisterDto userRegisterDto);

    String getCurrentUserEmail();
}
