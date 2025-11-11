package com.payment.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatisticsDTO {
    private long totalPayments;
    private long validPayments;
    private long invalidEmails;
    private long duplicatePayments;
    private long paymentsAboveThreshold;
}
