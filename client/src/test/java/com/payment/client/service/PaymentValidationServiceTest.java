package com.payment.client.service;

import com.payment.client.domain.Payment;
import com.payment.client.domain.ValidationResult;
import com.payment.client.dto.BookingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentValidationServiceTest {

    @Autowired
    private PaymentValidationService paymentValidationService;



    private BookingDTO newBooking;

    @BeforeEach
    void setUp() {
        newBooking = BookingDTO.builder()
                .reference("NEW-REF")
                .email("test@example.com")
                .amount(BigDecimal.valueOf(1000))
                .build();
    }

    @Test
    void validateDuplicatePayment_NoExistingPayments_ShouldReturnValid() {
        ValidationResult result = paymentValidationService.validateDuplicatePayment(newBooking, Collections.emptyList());

        assertTrue(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.NONE, result.getErrorType());
    }

    @Test
    void validateDuplicatePayment_WithDuplicateEmail_ShouldReturnInvalid() {
        Payment existingPayment = Payment.builder()
                .reference("OLD-REF")
                .email("test@example.com")
                .build();

        ValidationResult result = paymentValidationService.validateDuplicatePayment(newBooking, Collections.singletonList(existingPayment));

        assertFalse(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.DUPLICATED_PAYMENT, result.getErrorType());
    }

    @Test
    void validateDuplicatePayment_WithSameReference_ShouldReturnValid() {
        Payment existingPayment = Payment.builder()
                .reference("NEW-REF")
                .email("test@example.com")
                .build();

        ValidationResult result = paymentValidationService.validateDuplicatePayment(newBooking, Collections.singletonList(existingPayment));

        assertTrue(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.NONE, result.getErrorType());
    }

    @Test
    void validateDuplicatePayment_WithDifferentEmail_ShouldReturnValid() {
        Payment existingPayment = Payment.builder()
                .reference("OLD-REF")
                .email("other@example.com")
                .build();

        ValidationResult result = paymentValidationService.validateDuplicatePayment(newBooking, Collections.singletonList(existingPayment));

        assertTrue(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.NONE, result.getErrorType());
    }

    @Test
    void validateAmountThreshold_UnderThreshold_ShouldReturnValid() {
        ValidationResult result = paymentValidationService.validateAmountThreshold(BigDecimal.valueOf(500000));
        assertTrue(result.isValid());
    }

    @Test
    void validateAmountThreshold_OverThreshold_ShouldReturnInvalid() {
        ValidationResult result = paymentValidationService.validateAmountThreshold(BigDecimal.valueOf(1500000));
        assertFalse(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.AMOUNT_THRESHOLD_EXCEEDED, result.getErrorType());
    }

    @Test
    void validateOverUnderPayment_ExactAmount_ShouldReturnValid() {
        ValidationResult result = paymentValidationService.validateOverUnderPayment(BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
        assertTrue(result.isValid());
    }

    @Test
    void validateOverUnderPayment_OverPayment_ShouldReturnInvalid() {
        ValidationResult result = paymentValidationService.validateOverUnderPayment(BigDecimal.valueOf(1000), BigDecimal.valueOf(1100));
        assertFalse(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.OVER_PAYMENT, result.getErrorType());
    }

    @Test
    void validateOverUnderPayment_UnderPayment_ShouldReturnInvalid() {
        ValidationResult result = paymentValidationService.validateOverUnderPayment(BigDecimal.valueOf(1000), BigDecimal.valueOf(900));
        assertFalse(result.isValid());
        assertEquals(ValidationResult.ValidationErrorType.UNDER_PAYMENT, result.getErrorType());
    }
}
