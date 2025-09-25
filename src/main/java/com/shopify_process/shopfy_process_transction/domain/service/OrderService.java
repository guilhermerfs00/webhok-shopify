package com.shopify_process.shopfy_process_transction.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify_process.shopfy_process_transction.domain.client.BockbussClient;
import com.shopify_process.shopfy_process_transction.domain.exception.BusinessException;
import com.shopify_process.shopfy_process_transction.domain.exception.NotFoundException;
import com.shopify_process.shopfy_process_transction.domain.model.ApidogModelDTO;
import com.shopify_process.shopfy_process_transction.domain.model.WebhookNotificationDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private BockbussClient bockbussClient;

    public void processWebhookNotification(WebhookNotificationDTO payload) {
        
        try {
            ApidogModelDTO paymentResponse = bockbussClient.processPaymentExample(100.00,"BRL","ORDER-123");
            log.info("Payment processed successfully: {}", paymentResponse);

            //todo: retorar para o webhook a resposta do pagamento
            
        } catch (BusinessException | NotFoundException e) {
            log.error("Business error processing payment: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error processing payment: {}", e.getMessage());
            throw new BusinessException("Failed to process webhook notification: " + e.getMessage());
        }
    }
}
