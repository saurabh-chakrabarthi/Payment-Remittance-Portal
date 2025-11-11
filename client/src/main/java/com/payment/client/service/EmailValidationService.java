package com.payment.client.service;

import com.payment.client.domain.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailValidationService {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    public ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return ValidationResult.builder()
                    .valid(false)
                    .errorType(ValidationResult.ValidationErrorType.INVALID_EMAIL)
                    .message("Email cannot be empty")
                    .build();
        }
        
        boolean isValid = EMAIL_PATTERN.matcher(email).matches();
        return ValidationResult.builder()
                .valid(isValid)
                .errorType(isValid ? ValidationResult.ValidationErrorType.NONE : 
                         ValidationResult.ValidationErrorType.INVALID_EMAIL)
                .message(isValid ? "Valid email" : "Invalid email format")
                .build();
    }
}