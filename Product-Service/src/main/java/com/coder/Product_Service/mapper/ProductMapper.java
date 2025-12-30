package com.coder.Product_Service.mapper;

import com.coder.Product_Service.dto.CreateProductRequest;
import com.coder.Product_Service.dto.ProductResponse;
import com.coder.Product_Service.dto.UpdateProductRequest;
import com.coder.Product_Service.model.Product;

import java.util.stream.Collectors;

public class ProductMapper {

    // ===== CREATE =====
    public static Product toEntity(CreateProductRequest request) {
        if (request == null) return null;

        return Product.builder()
                .farmerId(request.getFarmerId())
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .variants(request.getVariants() != null ?
                        request.getVariants().stream().map(ProductMapper::toEntityVariant).collect(Collectors.toList())
                        : null)
                .images(request.getImages())
                .tags(request.getTags())
                .certifications(request.getCertifications())
                .harvestDate(request.getHarvestDate())
                .expiryDate(request.getExpiryDate())
                .location(request.getLocation())
                .status("ACTIVE")
                .build();
    }

    // ===== UPDATE =====
    public static void updateEntity(Product product, UpdateProductRequest request) {
        if (request == null || product == null) return;

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());

        if (request.getVariants() != null) {
            product.setVariants(request.getVariants()
                    .stream()
                    .map(ProductMapper::toEntityVariant)
                    .collect(Collectors.toList()));
        }

        product.setImages(request.getImages());
        product.setTags(request.getTags());
        product.setCertifications(request.getCertifications());
        product.setHarvestDate(request.getHarvestDate());
        product.setExpiryDate(request.getExpiryDate());
        product.setLocation(request.getLocation());
        product.setStatus(request.getStatus());
    }

    // ===== ENTITY → RESPONSE =====
    public static ProductResponse toResponse(Product product) {
        if (product == null) return null;

        return ProductResponse.builder()
                .id(product.getId())
                .farmerId(product.getFarmerId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .variants(product.getVariants() != null ?
                        product.getVariants().stream().map(ProductMapper::toResponseVariant).collect(Collectors.toList())
                        : null)
                .images(product.getImages())
                .tags(product.getTags())
                .certifications(product.getCertifications())
                .harvestDate(product.getHarvestDate())
                .expiryDate(product.getExpiryDate())
                .location(product.getLocation())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    // ===== VARIANT HELPERS =====
    private static Variant toEntityVariant(VariantRequest request) {
        if (request == null) return null;
        return Variant.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .unit(request.getUnit())
                .sku(request.getSku())
                .inventory(request.getInventory())
                .build();
    }

    private static VariantResponse toResponseVariant(Variant variant) {
        if (variant == null) return null;
        return VariantResponse.builder()
                .id(variant.getId())
                .name(variant.getName())
                .price(variant.getPrice())
                .unit(variant.getUnit())
                .sku(variant.getSku())
                .inventory(variant.getInventory())
                .build();
    }

    // ===== INVENTORY =====
    public static Inventory updateInventoryEntity(Inventory inventory, UpdateInventoryRequest request) {
        if (inventory == null || request == null) return inventory;

        inventory.setQuantity(request.getQuantity());
        inventory.setReserved(request.getReserved());
        inventory.setAvailable(request.getQuantity() - request.getReserved());
        return inventory;
    }
}
