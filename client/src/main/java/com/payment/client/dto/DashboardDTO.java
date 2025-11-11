package com.payment.client.dto;

import com.payment.client.domain.PaymentValidationResult;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private List<PaymentDTO> payments;
    private List<PaymentValidationResult> checkResults;
}