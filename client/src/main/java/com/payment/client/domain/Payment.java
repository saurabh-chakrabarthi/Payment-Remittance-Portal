package com.payment.client.domain;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String reference;
    private BigDecimal amount;
    private BigDecimal amountReceived;
    private String countryFrom;
    private String senderFullName;
    private String senderAddress;
    private String school;
    private String currencyFrom;
    private String studentId;
    private String email;
}
