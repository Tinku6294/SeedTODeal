package com.coder.Product_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateDTO {
    
    private String variantId;
    private Integer quantity;
    private Integer reserved;
}