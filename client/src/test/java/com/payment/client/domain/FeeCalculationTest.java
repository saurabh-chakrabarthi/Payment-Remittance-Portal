package com.payment.client.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class FeeCalculationTest {

    @Test
    void calculate_AmountUnder1000_ShouldApply5PercentFee() {
        FeeCalculation result = FeeCalculation.calculate(BigDecimal.valueOf(500));
        
        assertEquals(0, BigDecimal.valueOf(500).compareTo(result.getOriginalAmount()));
        assertEquals(0, BigDecimal.valueOf(5.00).compareTo(result.getFeePercentage()));
        assertEquals(0, BigDecimal.valueOf(25.00).compareTo(result.getFeeAmount()));
        assertEquals(0, BigDecimal.valueOf(525.00).compareTo(result.getTotalAmount()));
    }

    @Test
    void calculate_AmountBetween1000And10000_ShouldApply3PercentFee() {
        FeeCalculation result = FeeCalculation.calculate(BigDecimal.valueOf(5000));
        
        assertEquals(0, BigDecimal.valueOf(5000).compareTo(result.getOriginalAmount()));
        assertEquals(0, BigDecimal.valueOf(3.00).compareTo(result.getFeePercentage()));
        assertEquals(0, BigDecimal.valueOf(150.00).compareTo(result.getFeeAmount()));
        assertEquals(0, BigDecimal.valueOf(5150.00).compareTo(result.getTotalAmount()));
    }

    @Test
    void calculate_AmountOver10000_ShouldApply2PercentFee() {
        FeeCalculation result = FeeCalculation.calculate(BigDecimal.valueOf(50000));
        
        assertEquals(0, BigDecimal.valueOf(50000).compareTo(result.getOriginalAmount()));
        assertEquals(0, BigDecimal.valueOf(2.00).compareTo(result.getFeePercentage()));
        assertEquals(0, BigDecimal.valueOf(1000.00).compareTo(result.getFeeAmount()));
        assertEquals(0, BigDecimal.valueOf(51000.00).compareTo(result.getTotalAmount()));
    }

    @Test
    void calculate_BoundaryAmount1000_ShouldApply3PercentFee() {
        FeeCalculation result = FeeCalculation.calculate(BigDecimal.valueOf(1000));
        
        assertEquals(0, BigDecimal.valueOf(3.00).compareTo(result.getFeePercentage()));
        assertEquals(0, BigDecimal.valueOf(30.00).compareTo(result.getFeeAmount()));
    }

    @Test
    void calculate_BoundaryAmount10000_ShouldApply2PercentFee() {
        FeeCalculation result = FeeCalculation.calculate(BigDecimal.valueOf(10000));
        
        assertEquals(0, BigDecimal.valueOf(2.00).compareTo(result.getFeePercentage()));
        assertEquals(0, BigDecimal.valueOf(200.00).compareTo(result.getFeeAmount()));
    }
}