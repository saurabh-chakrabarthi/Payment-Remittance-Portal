package com.payment.client.domain;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Represents a quality check for a payment.
 * Quality checks include:
 * - Email validation
 * - Duplicate payment check
 * - Amount threshold check (> ,000,000)
 * - Over/Under payment check
 */
@Data
@Builder
public class QualityCheck {
    private static final BigDecimal AMOUNT_THRESHOLD = BigDecimal.valueOf(1_000_000);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");


    private boolean validEmail;
    private boolean duplicate;
    private boolean aboveThreshold;
    private PaymentStatus paymentStatus;
    private FeeCalculation feeCalculation;
    private String message;

    /**
     * Checks if any quality rules are violated
     * @return true if all quality checks pass, false otherwise
     */
    public boolean isValid() {
        return validEmail && !duplicate && !aboveThreshold &&
               paymentStatus == PaymentStatus.EXACT;
    }

    /**
     * Creates a quality check result for a payment.
     * @param payment The payment to check
     * @param existingPayments List of existing payments to check for duplicates
     * @return QualityCheck result
     */
    public static QualityCheck check(Payment payment, Payment... existingPayments) {
        boolean validEmail = isValidEmail(payment.getEmail());
        boolean duplicate = isDuplicate(payment, existingPayments);
        boolean aboveThreshold = isAboveThreshold(payment.getAmount());
        PaymentStatus paymentStatus = determinePaymentStatus(payment);
        FeeCalculation feeCalculation = calculateFees(payment.getAmount());

        StringBuilder message = new StringBuilder();
        if (!validEmail) message.append("Invalid email. ");
        if (duplicate) message.append("Duplicate payment found. ");
        if (aboveThreshold) message.append("Amount exceeds threshold. ");
        if (paymentStatus != PaymentStatus.EXACT) {
            message.append(paymentStatus == PaymentStatus.OVERPAYMENT ?
                "Over payment detected. " : "Under payment detected. ");
        }

        return QualityCheck.builder()
                .validEmail(validEmail)
                .duplicate(duplicate)
                .aboveThreshold(aboveThreshold)
                .paymentStatus(paymentStatus)
                .feeCalculation(feeCalculation)
                .message(message.length() > 0 ? message.toString().trim() : "All checks passed")
                .build();
    }

    private static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private static boolean isDuplicate(Payment payment, Payment... existingPayments) {
        if (existingPayments == null || existingPayments.length == 0) {
            return false;
        }
        return java.util.Arrays.stream(existingPayments)
                .anyMatch(p -> p.getEmail() != null &&
                        p.getEmail().equalsIgnoreCase(payment.getEmail()) &&
                        p.getAmount().equals(payment.getAmount()));
    }

    private static boolean isAboveThreshold(BigDecimal amount) {
        return amount.compareTo(AMOUNT_THRESHOLD) > 0;
    }

    private static PaymentStatus determinePaymentStatus(Payment payment) {
        int comparison = payment.getAmountReceived().compareTo(payment.getAmount());
        if (comparison > 0) {
            return PaymentStatus.OVERPAYMENT;
        } else if (comparison < 0) {
            return PaymentStatus.UNDERPAYMENT;
        }
        return PaymentStatus.EXACT;
    }

    private static FeeCalculation calculateFees(BigDecimal amount) {
        return FeeCalculation.calculate(amount);
    }
}

