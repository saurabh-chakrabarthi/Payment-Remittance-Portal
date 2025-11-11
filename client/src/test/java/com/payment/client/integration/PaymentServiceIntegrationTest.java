package com.payment.client.integration;

import com.payment.client.domain.Payment;
import com.payment.client.domain.PaymentValidationResult;
import com.payment.client.dto.BookingDTO;
import com.payment.client.dto.BookingResponse;
import com.payment.client.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private PaymentApiClient paymentApiClient;

    private BookingDTO bookingDTO;

    @BeforeEach
    void setUp() {
        bookingDTO = BookingDTO.builder()
                .reference("TEST-REF")
                .email("test@example.com")
                .amount(BigDecimal.valueOf(1000))
                .amountReceived(BigDecimal.valueOf(1000))
                .build();
    }

    @Test
    void getAllPayments_ShouldReturnPayments() {
        BookingResponse bookingResponse = new BookingResponse(Collections.singletonList(bookingDTO));
        when(paymentApiClient.getBookings()).thenReturn(bookingResponse);

        List<Payment> payments = paymentService.getAllPayments();

        assertNotNull(payments);
        assertEquals(1, payments.size());
        assertEquals("TEST-REF", payments.get(0).getReference());
    }

    @Test
    void validatePayment_WithValidPayment_ShouldPassAllChecks() {
        when(paymentApiClient.getBookings()).thenReturn(new BookingResponse(Collections.emptyList()));

        PaymentValidationResult result = paymentService.validatePayment(bookingDTO);

        assertNotNull(result);
        assertTrue(result.getEmailValidation().isValid());
        assertTrue(result.getDuplicatePaymentValidation().isValid());
        assertTrue(result.getAmountThresholdValidation().isValid());
        assertTrue(result.getOverUnderPaymentValidation().isValid());
    }

    @Test
    void validatePayment_WithDuplicatePayment_ShouldFailDuplicateCheck() {
        BookingDTO existingBooking = BookingDTO.builder()
                .reference("EXISTING-REF")
                .email("test@example.com")
                .amount(BigDecimal.valueOf(500))
                .amountReceived(BigDecimal.valueOf(500))
                .build();
        
        BookingResponse bookingResponse = new BookingResponse(Collections.singletonList(existingBooking));
        when(paymentApiClient.getBookings()).thenReturn(bookingResponse);

        PaymentValidationResult result = paymentService.validatePayment(bookingDTO);

        assertNotNull(result);
        assertFalse(result.getDuplicatePaymentValidation().isValid());
    }
}
