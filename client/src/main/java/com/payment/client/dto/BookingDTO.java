package com.payment.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    @JsonProperty("reference")
    private String reference;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("amount_received")
    private BigDecimal amountReceived;

    @JsonProperty("country_from")
    private String countryFrom;

    @JsonProperty("sender_full_name")
    private String senderFullName;

    @JsonProperty("sender_address")
    private String senderAddress;

    @JsonProperty("school")
    private String school;

    @JsonProperty("currency_from")
    private String currencyFrom;

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("email")
    private String email;
}