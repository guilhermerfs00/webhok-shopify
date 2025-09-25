package com.shopify_process.shopfy_process_transction.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApidogModelDTO {

    private String id;
    private String amount;
    private String currency;
    private String status;
    private String createdAt;
    private String updatedAt;
    
}
