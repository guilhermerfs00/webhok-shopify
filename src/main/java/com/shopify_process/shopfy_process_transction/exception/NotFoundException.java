package com.shopify_process.shopfy_process_transction.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção para recursos não encontrados
 */
public class NotFoundException extends BaseException {
    
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }
    
    /**
     * Construtor de conveniência para recursos não encontrados por ID
     * @param resourceName nome do recurso
     * @param resourceId identificador do recurso
     */
    public NotFoundException(String resourceName, Object resourceId) {
        super(HttpStatus.NOT_FOUND, String.format("%s with id '%s' not found", resourceName, resourceId));
    }
}