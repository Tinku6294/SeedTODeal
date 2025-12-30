package com.coder.Product_Service.model;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Variant {
    private String id;
    private String name;
    private Double price;
    private String unit;
    private String sku;
    private Inventory inventory;
}
