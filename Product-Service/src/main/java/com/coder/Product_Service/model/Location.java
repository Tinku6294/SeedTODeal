package com.coder.Product_Service.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    private String state;
    private String district;
    private Double lat;
    private Double lng;
}
