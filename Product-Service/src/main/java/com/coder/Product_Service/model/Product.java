package com.coder.Product_Service.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private Long farmerId;
    private String name;
    private String description;
    private Category category;          // now top-level
    private List<Variant> variants;     // top-level
    private List<String> images;
    private List<String> tags;
    private List<String> certifications;
    private Date harvestDate;
    private Date expiryDate;
    private Location location;          // top-level
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
