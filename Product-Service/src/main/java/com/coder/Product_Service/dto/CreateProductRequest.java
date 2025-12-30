package com.coder.Product_Service.dto;

import com.coder.Product_Service.model.Category;
import com.coder.Product_Service.model.Location;
import lombok.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {
    private Long farmerId;
    private String name;
    private String description;
    private Category category;
    private List<VariantRequest> variants;
    private List<String> images;
    private List<String> tags;
    private List<String> certifications;
    private Date harvestDate;
    private Date expiryDate;
    private Location location;
}
