package com.shopify_process.shopfy_process_transction.domain.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shopify_process.shopfy_process_transction.domain.exception.BusinessException;
import com.shopify_process.shopfy_process_transction.domain.exception.NotFoundException;
import com.shopify_process.shopfy_process_transction.domain.model.ApidogModelDTO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;

@Service
@Slf4j
public class BockbussClient {
    
    private static final String BUCKSBUS_API_URL = "https://api.bucksbus.com/int/payment";
    
    @Autowired
    private RestTemplate restTemplate;
  
    public ApidogModelDTO processPayment(ApidogModelDTO paymentRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");
            headers.set("Authorization", "Bearer YOUR_API_KEY");
            
            HttpEntity<ApidogModelDTO> request = new HttpEntity<>(paymentRequest, headers);

            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> response = restTemplate.exchange(
                BUCKSBUS_API_URL,
                HttpMethod.POST,
                request,
                Map.class
            );
            
            var responseBody = (ApidogModelDTO) response.getBody();
            
            return responseBody;
            
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException("Payment endpoint not found: " + BUCKSBUS_API_URL);
            } else if (e.getStatusCode().is4xxClientError()) {
                throw new BusinessException("Invalid payment request: " + e.getResponseBodyAsString());
            } else {
                throw new BusinessException(HttpStatus.valueOf(e.getStatusCode().value()), "Payment processing failed: " + e.getResponseBodyAsString());
            }
        } catch (ResourceAccessException e) {
            throw new BusinessException(HttpStatus.SERVICE_UNAVAILABLE, "Bucksbus API is unavailable: " + e.getMessage());
        } catch (Exception e) {
            // Tratar outros erros
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error processing payment: " + e.getMessage());
        }
    }
 
    public ApidogModelDTO processPaymentExample(Double amount, String currency, String orderId) {
        ApidogModelDTO paymentRequest = ApidogModelDTO.builder()
        .amount(String.valueOf(amount))
        .currency(currency)
        .id(orderId)
        .status("pending")
        .build();
        
        return processPayment(paymentRequest);
    }
}
