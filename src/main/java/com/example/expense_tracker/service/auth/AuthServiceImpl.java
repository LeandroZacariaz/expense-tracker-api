package com.example.expense_tracker.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.expense_tracker.domain.User;
import com.example.expense_tracker.dto.auth.AuthResponseDto;
import com.example.expense_tracker.dto.user.UserLoginDto;
import com.example.expense_tracker.dto.user.UserRegisterDto;
import com.example.expense_tracker.exceptions.EmailAlreadyExistsException;
import com.example.expense_tracker.exceptions.InvalidCredentialsException;
import com.example.expense_tracker.exceptions.ResourceNotFoundException;
import com.example.expense_tracker.repository.UserRepository;
import com.example.expense_tracker.service.jwt.JwtService;
import com.example.expense_tracker.service.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
    private UserService userService;
    private JwtService jwtService;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    
    @Override
    public AuthResponseDto login(UserLoginDto userLoginDto) {
        try {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.email(), userLoginDto.password()));
      } catch (BadCredentialsException var4) {
         throw new InvalidCredentialsException("Invalid email or password.");
      }

      UserDetails user = userRepository.findByEmail(userLoginDto.email()).orElseThrow(() -> {
         return new ResourceNotFoundException("User not found with email: " + userLoginDto.email());
      });
      String token = jwtService.getToken(user);
      return AuthResponseDto.builder().token(token).build();
    }

    @Override
    public AuthResponseDto register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByEmail(userRegisterDto.email())) {
            throw new EmailAlreadyExistsException("The email " + userRegisterDto.email() + " is already registered.");
         } else {
            User user = userService.createUser(userRegisterDto);
            return AuthResponseDto.builder().token(jwtService.getToken(user)).build();
         }
    }

    @Override
    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
