package com.payment.client.config;

import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Configuration
public class RulesConfig {
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    // Fee calculation thresholds
    private static final BigDecimal TIER1_THRESHOLD = new BigDecimal("1000");
    private static final BigDecimal TIER2_THRESHOLD = new BigDecimal("10000");
    
    // Fee percentages
    private static final BigDecimal TIER1_FEE = new BigDecimal("5.0");
    private static final BigDecimal TIER2_FEE = new BigDecimal("3.0");
    private static final BigDecimal TIER3_FEE = new BigDecimal("2.0");
    
    // Amount threshold for flagging high-value payments
    private static final BigDecimal HIGH_VALUE_THRESHOLD = new BigDecimal("1000000");
    
    // Getters for all constants
    public Pattern getEmailPattern() {
        return EMAIL_PATTERN;
    }
    
    public BigDecimal getTier1Threshold() {
        return TIER1_THRESHOLD;
    }
    
    public BigDecimal getTier2Threshold() {
        return TIER2_THRESHOLD;
    }
    
    public BigDecimal getTier1Fee() {
        return TIER1_FEE;
    }
    
    public BigDecimal getTier2Fee() {
        return TIER2_FEE;
    }
    
    public BigDecimal getTier3Fee() {
        return TIER3_FEE;
    }
    
    public BigDecimal getHighValueThreshold() {
        return HIGH_VALUE_THRESHOLD;
    }
}
