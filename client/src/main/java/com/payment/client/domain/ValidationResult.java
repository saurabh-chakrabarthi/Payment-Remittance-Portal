package com.payment.client.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResult {
    private boolean valid;
    private String message;
    private ValidationErrorType errorType;

    public enum ValidationErrorType {
        INVALID_EMAIL,
        DUPLICATED_PAYMENT,
        AMOUNT_THRESHOLD_EXCEEDED,
        OVER_PAYMENT,
        UNDER_PAYMENT,
        NONE
    }
}
