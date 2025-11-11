package com.payment.client.integration;

import com.payment.client.domain.Payment;
import com.payment.client.dto.BookingDTO;
import com.payment.client.dto.PaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment toPayment(BookingDTO booking) {
        return Payment.builder()
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
                .build();
    }

    public BookingDTO toBookingDTO(Payment payment) {
        return BookingDTO.builder()
                .reference(payment.getReference())
                .amount(payment.getAmount())
                .amountReceived(payment.getAmountReceived())
                .countryFrom(payment.getCountryFrom())
                .senderFullName(payment.getSenderFullName())
                .senderAddress(payment.getSenderAddress())
                .school(payment.getSchool())
                .currencyFrom(payment.getCurrencyFrom())
                .studentId(payment.getStudentId())
                .email(payment.getEmail())
                .build();
    }

    public PaymentDTO toPaymentDTO(Payment payment) {
        return PaymentDTO.builder()
                .reference(payment.getReference())
                .amount(payment.getAmount())
                .amountReceived(payment.getAmountReceived())
                .email(payment.getEmail())
                .senderFullName(payment.getSenderFullName())
                .senderAddress(payment.getSenderAddress())
                .school(payment.getSchool())
                .currencyFrom(payment.getCurrencyFrom())
                .countryFrom(payment.getCountryFrom())
                .studentId(payment.getStudentId())
                .build();
    }
}
