package com.shopify_process.shopfy_process_transction.controller;

import com.shopify_process.shopfy_process_transction.domain.model.WebhookNotificationDTO;
import com.shopify_process.shopfy_process_transction.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class WebhookController {

    private final OrderService orderService;

    @PostMapping(value = "/webhook", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void webhook(@RequestBody WebhookNotificationDTO payload) {
        log.info("Webhook Notification Received: {}", payload);

        orderService.processWebhookNotification(payload);
    }
}
