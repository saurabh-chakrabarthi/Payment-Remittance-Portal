package com.payment.client.service;

import com.payment.client.domain.PaymentValidationResult;
import com.payment.client.dto.BookingDTO;
import com.payment.client.dto.BookingResponse;
import com.payment.client.integration.PaymentApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private PaymentApiClient paymentApiClient;

    @Test
    void validatePayment_HappyPath_AllValidationsPassed() {
        when(paymentApiClient.getBookings()).thenReturn(new BookingResponse(Collections.emptyList()));
        
        BookingDTO booking = BookingDTO.builder()
                .reference("REF-123")
                .email("valid@email.com")
                .amount(BigDecimal.valueOf(500))
                .amountReceived(BigDecimal.valueOf(500))
                .build();

        PaymentValidationResult result = paymentService.validatePayment(booking);

        assertTrue(result.getEmailValidation().isValid());
        assertTrue(result.getAmountThresholdValidation().isValid());
        assertTrue(result.getOverUnderPaymentValidation().isValid());
        assertEquals("EXACT", result.getPaymentStatus());
        assertEquals(5, result.getFeePercentage());
    }

    @Test
    void validatePayment_InvalidEmail_ShouldFailEmailValidation() {
        when(paymentApiClient.getBookings()).thenReturn(new BookingResponse(Collections.emptyList()));
        
        BookingDTO booking = BookingDTO.builder()
                .reference("REF-123")
                .email("invalid-email")
                .amount(BigDecimal.valueOf(500))
                .amountReceived(BigDecimal.valueOf(500))
                .build();

        PaymentValidationResult result = paymentService.validatePayment(booking);

        assertFalse(result.getEmailValidation().isValid());
        assertFalse(result.isValidEmail());
    }

    @Test
    void validatePayment_AmountAboveThreshold_ShouldFailThresholdValidation() {
        when(paymentApiClient.getBookings()).thenReturn(new BookingResponse(Collections.emptyList()));
        
        BookingDTO booking = BookingDTO.builder()
                .reference("REF-123")
                .email("valid@email.com")
                .amount(BigDecimal.valueOf(1500000))
                .amountReceived(BigDecimal.valueOf(1500000))
                .build();

        PaymentValidationResult result = paymentService.validatePayment(booking);

        assertFalse(result.getAmountThresholdValidation().isValid());
        assertTrue(result.isAboveThreshold());
        assertEquals(2, result.getFeePercentage());
    }
}