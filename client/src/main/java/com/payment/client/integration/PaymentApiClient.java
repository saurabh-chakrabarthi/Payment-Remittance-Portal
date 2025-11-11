package com.payment.client.integration;

import com.payment.client.domain.Payment;
import com.payment.client.dto.BookingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentApiClient {
    private final RestTemplate restTemplate;
    private final String apiBaseUrl;

    public PaymentApiClient(RestTemplate restTemplate, @Value("${api.base-url}") String apiBaseUrl) {
        this.restTemplate = restTemplate;
        this.apiBaseUrl = apiBaseUrl;
    }

    public BookingResponse getBookings() {
        return restTemplate.getForObject(apiBaseUrl + "/api/bookings", BookingResponse.class);
    }
}
