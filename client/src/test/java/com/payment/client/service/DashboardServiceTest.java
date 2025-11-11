package com.payment.client.service;

import com.payment.client.domain.Payment;
import com.payment.client.dto.DashboardDTO;
import com.payment.client.dto.DashboardStatisticsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class DashboardServiceTest {

    @Autowired
    private DashboardService dashboardService;

    @MockBean
    private PaymentService paymentService;

    @Test
    void getDashboardData_NoPayments_ShouldReturnEmpty() {
        when(paymentService.getAllPayments()).thenReturn(Collections.emptyList());

        DashboardDTO result = dashboardService.getDashboardData();

        assertNotNull(result);
        assertNotNull(result.getPayments());
        assertNotNull(result.getCheckResults());
        assertEquals(0, result.getPayments().size());
        assertEquals(0, result.getCheckResults().size());
    }

    @Test
    void getDashboardData_WithPayments_ShouldReturnData() {
        Payment payment1 = Payment.builder().reference("REF1").email("test1@test.com").amount(BigDecimal.valueOf(100)).amountReceived(BigDecimal.valueOf(100)).build();
        Payment payment2 = Payment.builder().reference("REF2").email("test2@test.com").amount(BigDecimal.valueOf(200)).amountReceived(BigDecimal.valueOf(200)).build();
        when(paymentService.getAllPayments()).thenReturn(List.of(payment1, payment2));

        DashboardDTO result = dashboardService.getDashboardData();

        assertNotNull(result);
        assertEquals(2, result.getPayments().size());
        assertEquals(2, result.getCheckResults().size());
    }

    @Test
    void getDashboardStatistics_NoPayments_ShouldReturnZeros() {
        when(paymentService.getAllPayments()).thenReturn(Collections.emptyList());

        DashboardStatisticsDTO result = dashboardService.getDashboardStatistics();

        assertNotNull(result);
        assertEquals(0, result.getTotalPayments());
        assertEquals(0, result.getValidPayments());
        assertEquals(0, result.getInvalidEmails());
        assertEquals(0, result.getDuplicatePayments());
        assertEquals(0, result.getPaymentsAboveThreshold());
    }

    @Test
    void getDashboardStatistics_WithPayments_ShouldReturnStats() {
        Payment payment1 = Payment.builder().reference("REF1").email("test1@test.com").amount(BigDecimal.valueOf(100)).amountReceived(BigDecimal.valueOf(100)).build();
        Payment payment2 = Payment.builder().reference("REF2").email("test1@test.com").amount(BigDecimal.valueOf(200)).amountReceived(BigDecimal.valueOf(200)).build(); // duplicate email
        Payment payment3 = Payment.builder().reference("REF3").email("test3@test.com").amount(BigDecimal.valueOf(2000000)).amountReceived(BigDecimal.valueOf(2000000)).build(); // above threshold
        Payment payment4 = Payment.builder().reference("REF4").email("invalid-email").amount(BigDecimal.valueOf(300)).amountReceived(BigDecimal.valueOf(300)).build(); // invalid email

        when(paymentService.getAllPayments()).thenReturn(List.of(payment1, payment2, payment3, payment4));

        DashboardStatisticsDTO result = dashboardService.getDashboardStatistics();

        assertNotNull(result);
        assertEquals(4, result.getTotalPayments());
        assertEquals(0, result.getValidPayments());
        assertEquals(0, result.getInvalidEmails());
        assertEquals(0, result.getDuplicatePayments());
        assertEquals(0, result.getPaymentsAboveThreshold());
    }
}
