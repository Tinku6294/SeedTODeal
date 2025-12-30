package com.coder.Product_Service.model;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    private Integer quantity;
    private Integer reserved;
    private Integer available;
}
