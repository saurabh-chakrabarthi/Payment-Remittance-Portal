package com.payment.client.domain;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class FeeCalculation {
    private BigDecimal originalAmount;
    private BigDecimal feePercentage;
    private BigDecimal feeAmount;
    private BigDecimal totalAmount;
    
    public static FeeCalculation calculate(BigDecimal amount) {
        BigDecimal feePercentage = determineFeePercentage(amount);
        BigDecimal feeAmount = amount.multiply(feePercentage);
        
        return FeeCalculation.builder()
                .originalAmount(amount)
                .feePercentage(feePercentage.multiply(BigDecimal.valueOf(100))) // Convert to percentage
                .feeAmount(feeAmount)
                .totalAmount(amount.add(feeAmount))
                .build();
    }
    
    private static BigDecimal determineFeePercentage(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(1000)) < 0) {
            return BigDecimal.valueOf(0.05); // 5%
        } else if (amount.compareTo(BigDecimal.valueOf(10000)) < 0) {
            return BigDecimal.valueOf(0.03); // 3%
        } else {
            return BigDecimal.valueOf(0.02); // 2%
        }
    }
}
