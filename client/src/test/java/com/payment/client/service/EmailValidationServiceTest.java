package com.payment.client.service;

import com.payment.client.domain.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidationServiceTest {

    private EmailValidationService emailValidationService;

    @BeforeEach
    void setUp() {
        emailValidationService = new EmailValidationService();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test@example.com",
            "user.name@domain.com",
            "user+label@domain.com"
    })
    void validateEmail_ValidEmails_ReturnsValid(String email) {
        ValidationResult result = emailValidationService.validateEmail(email);

        assertTrue(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.NONE, result.getErrorType());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "invalid-email",
            "@domain.com",
            "user@",
            "user@domain"
    })
    void validateEmail_InvalidEmails_ReturnsInvalid(String email) {
        ValidationResult result = emailValidationService.validateEmail(email);

        assertFalse(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.INVALID_EMAIL, result.getErrorType());
    }

    @Test
    void validateEmail_NullEmail_ReturnsInvalid() {
        ValidationResult result = emailValidationService.validateEmail(null);

        assertFalse(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.INVALID_EMAIL, result.getErrorType());
    }
}
