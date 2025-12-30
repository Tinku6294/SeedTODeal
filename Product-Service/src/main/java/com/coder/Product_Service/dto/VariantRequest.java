package com.coder.Product_Service.dto;

import com.coder.Product_Service.model.Inventory;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantRequest {
    private String id;
    private String name;
    private Double price;
    private String unit; // kg, liter, piece
    private String sku;
    private Inventory inventory;
}
