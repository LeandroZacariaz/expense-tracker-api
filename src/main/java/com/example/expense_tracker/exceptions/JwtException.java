package com.example.expense_tracker.exceptions;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
       super(message);
    }
 }
 