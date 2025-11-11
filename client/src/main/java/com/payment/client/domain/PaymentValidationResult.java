package com.payment.client.domain;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentValidationResult {
    private ValidationResult emailValidation;
    private ValidationResult duplicatePaymentValidation;
    private ValidationResult amountThresholdValidation;
    private ValidationResult overUnderPaymentValidation;
    private FeeCalculation feeCalculation;
    private String paymentStatus;
    private boolean validEmail;
    private boolean duplicate;
    private boolean aboveThreshold;
    private int feePercentage;
    private BigDecimal feeAmount;
    private BigDecimal finalAmount;
    
    public boolean hasErrors() {
        return !emailValidation.isValid() ||
               !duplicatePaymentValidation.isValid() ||
               !amountThresholdValidation.isValid() ||
               !overUnderPaymentValidation.isValid();
    }
}