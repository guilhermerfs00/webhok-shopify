package com.shopify_process.shopfy_process_transction.domain.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;
import java.util.HashMap;

@Service
public class BockbussClient {
    
    private static final String BUCKSBUS_API_URL = "https://api.bucksbus.com/int/payment";
    
    @Autowired
    private RestTemplate restTemplate;
  
    public Map<String, Object> processPayment(Map<String, Object> paymentRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");
            headers.set("Authorization", "Bearer YOUR_API_KEY"); // Adicione se necessário
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(paymentRequest, headers);

            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> response = restTemplate.exchange(
                BUCKSBUS_API_URL,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            @SuppressWarnings("unchecked")
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            
            return responseBody;
            
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erro na chamada para API Bucksbus: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (ResourceAccessException e) {
            throw new RuntimeException("Erro de conectividade com API Bucksbus: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao chamar API Bucksbus: " + e.getMessage(), e);
        }
    }
 
    public Map<String, Object> processPaymentExample(Double amount, String currency, String orderId) {
        Map<String, Object> paymentRequest = new HashMap<>();
        paymentRequest.put("amount", amount);
        paymentRequest.put("currency", currency);
        paymentRequest.put("order_id", orderId);
        paymentRequest.put("payment_method", "credit_card");
        paymentRequest.put("timestamp", System.currentTimeMillis());
        
        return processPayment(paymentRequest);
    }
}
