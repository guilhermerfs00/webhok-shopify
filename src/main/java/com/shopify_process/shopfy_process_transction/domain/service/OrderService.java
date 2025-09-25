package com.shopify_process.shopfy_process_transction.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify_process.shopfy_process_transction.domain.client.BockbussClient;
import com.shopify_process.shopfy_process_transction.domain.model.WebhookNotificationDTO;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private BockbussClient bockbussClient;

    public void processWebhookNotification(WebhookNotificationDTO payload) {
        try {
            Map<String, Object> paymentResponse = bockbussClient.processPaymentExample(100.00,"BRL","ORDER-123");
            log.info("Payment processed successfully: {}", paymentResponse);
            
        } catch (Exception e) {
            log.error("Error processing payment: {}", e.getMessage());
        }
    }
}
