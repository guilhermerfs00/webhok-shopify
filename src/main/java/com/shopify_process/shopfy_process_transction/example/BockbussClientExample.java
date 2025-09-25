package com.shopify_process.shopfy_process_transction.example;

import com.shopify_process.shopfy_process_transction.domain.client.BockbussClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Exemplo de uso do BockbussClient
 * Esta classe demonstra como fazer chamadas HTTP POST para a API do Bucksbus
 */
@Component
public class BockbussClientExample implements CommandLineRunner {

    @Autowired
    private BockbussClient bockbussClient;

    @Override
    public void run(String... args) throws Exception {
        // Descomente as linhas abaixo para testar o cliente
        // exemploProcessarPagamento();
        // exemploComDadosPersonalizados();
    }

    /**
     * Exemplo simples usando o método processPaymentExample
     */
    public void exemploProcessarPagamento() {
        System.out.println("=== Exemplo 1: Processamento de Pagamento Simples ===");
        
        try {
            Map<String, Object> response = bockbussClient.processPaymentExample(
                150.75,        // valor
                "BRL",         // moeda
                "ORD-2024-001" // ID do pedido
            );
            
            System.out.println("✅ Sucesso! Resposta: " + response);
            
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }

    /**
     * Exemplo avançado com dados personalizados
     */
    public void exemploComDadosPersonalizados() {
        System.out.println("\n=== Exemplo 2: Pagamento com Dados Personalizados ===");
        
        try {
            // Criar payload personalizado
            Map<String, Object> paymentData = new HashMap<>();
            paymentData.put("amount", 299.99);
            paymentData.put("currency", "BRL");
            paymentData.put("order_id", "SHOP-2024-12345");
            paymentData.put("payment_method", "pix");
            paymentData.put("customer", Map.of(
                "email", "cliente@exemplo.com",
                "name", "João Silva",
                "phone", "+5511999999999"
            ));
            paymentData.put("metadata", Map.of(
                "source", "shopify",
                "webhook_id", "webhook_123",
                "processed_at", System.currentTimeMillis()
            ));
            
            Map<String, Object> response = bockbussClient.processPayment(paymentData);
            
            System.out.println("✅ Sucesso! Resposta: " + response);
            
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
}