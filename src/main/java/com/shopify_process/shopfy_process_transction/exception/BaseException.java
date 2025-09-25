package com.shopify_process.shopfy_process_transction.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

/**
 * Classe base para todas as exceções personalizadas da aplicação
 */
@Getter
public abstract class BaseException extends RuntimeException {
    
    private final HttpStatus httpStatus;
    private final String message;
    
    public BaseException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
    
    public BaseException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}