package com.payment.client.service;

import com.payment.client.domain.Payment;
import com.payment.client.domain.PaymentValidationResult;
import com.payment.client.dto.DashboardDTO;
import com.payment.client.dto.DashboardStatisticsDTO;
import com.payment.client.dto.PaymentDTO;
import com.payment.client.integration.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    public DashboardDTO getDashboardData() {
        List<Payment> payments = paymentService.getAllPayments();
        List<PaymentDTO> paymentDTOs = payments.stream()
                .map(paymentMapper::toPaymentDTO)
                .collect(Collectors.toList());

        List<PaymentValidationResult> paymentValidationResults = payments.stream()
                .map(payment -> paymentService.validatePayment(paymentMapper.toBookingDTO(payment)))
                .collect(Collectors.toList());

        return DashboardDTO.builder()
                .payments(paymentDTOs)
                .checkResults(paymentValidationResults)
                .build();
    }

    public DashboardStatisticsDTO getDashboardStatistics() {
        List<Payment> payments = paymentService.getAllPayments();
        List<PaymentValidationResult> validationResults = payments.stream()
                .map(payment -> paymentService.validatePayment(paymentMapper.toBookingDTO(payment)))
                .collect(Collectors.toList());

        long totalPayments = validationResults.size();
        long validPayments = validationResults.stream().filter(r -> r != null && !r.hasErrors()).count();
        long invalidEmails = validationResults.stream().filter(r -> r != null && !r.getEmailValidation().isValid()).count();
        long duplicatePayments = validationResults.stream().filter(r -> r != null && !r.getDuplicatePaymentValidation().isValid()).count();
        long aboveThreshold = validationResults.stream().filter(r -> r != null && !r.getAmountThresholdValidation().isValid()).count();

        return DashboardStatisticsDTO.builder()
                .totalPayments(totalPayments)
                .validPayments(validPayments)
                .invalidEmails(invalidEmails)
                .duplicatePayments(duplicatePayments)
                .paymentsAboveThreshold(aboveThreshold)
                .build();
    }
}