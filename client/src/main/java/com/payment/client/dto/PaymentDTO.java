package com.payment.client.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentDTO {
    private String reference;
    private BigDecimal amount;
    private BigDecimal amountReceived;
    private String email;
    private String senderFullName;
    private String senderAddress;
    private String school;
    private String currencyFrom;
    private String countryFrom;
    private String studentId;
}
