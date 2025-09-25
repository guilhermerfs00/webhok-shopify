package com.shopify_process.shopfy_process_transction.domain.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {
    
    public BusinessException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }
    
    public BusinessException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
    
    public BusinessException(HttpStatus httpStatus, String message, Throwable cause) {
        super(httpStatus, message, cause);
    }
}