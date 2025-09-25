package com.shopify_process.shopfy_process_transction.domain.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }

    public NotFoundException(String resourceName, Object resourceId) {
        super(HttpStatus.NOT_FOUND, String.format("%s with id '%s' not found", resourceName, resourceId));
    }
}