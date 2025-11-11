package com.payment.client.controller;

import com.payment.client.domain.PaymentValidationResult;
import com.payment.client.dto.BookingDTO;
import com.payment.client.dto.DashboardDTO;
import com.payment.client.dto.PaymentDTO;
import com.payment.client.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final PaymentService paymentService;

    @GetMapping("/")
    public String dashboard(Model model) {
        List<BookingDTO> bookings = paymentService.getAllPayments().stream()
                .map(payment -> BookingDTO.builder()
                        .reference(payment.getReference())
                        .amount(payment.getAmount())
                        .amountReceived(payment.getAmountReceived())
                        .countryFrom(payment.getCountryFrom())
                        .senderFullName(payment.getSenderFullName())
                        .senderAddress(payment.getSenderAddress())
                        .school(payment.getSchool())
                        .currencyFrom(payment.getCurrencyFrom())
                        .studentId(String.valueOf(payment.getStudentId()))
                        .email(payment.getEmail())
                        .build())
                .collect(Collectors.toList());

        List<PaymentValidationResult> checkResults = bookings.stream()
                .map(paymentService::validatePayment)
                .collect(Collectors.toList());

        DashboardDTO dashboardDTO = DashboardDTO.builder()
                .payments(bookings.stream()
                        .map(booking -> PaymentDTO.builder()
                                .reference(booking.getReference())
                                .amount(booking.getAmount())
                                .amountReceived(booking.getAmountReceived())
                                .countryFrom(booking.getCountryFrom())
                                .senderFullName(booking.getSenderFullName())
                                .senderAddress(booking.getSenderAddress())
                                .school(booking.getSchool())
                                .currencyFrom(booking.getCurrencyFrom())
                                .studentId(booking.getStudentId())
                                .email(booking.getEmail())
                                .build())
                        .collect(Collectors.toList()))
                .checkResults(checkResults)
                .build();

        model.addAttribute("dashboard", dashboardDTO);
        return "dashboard";
    }
}
