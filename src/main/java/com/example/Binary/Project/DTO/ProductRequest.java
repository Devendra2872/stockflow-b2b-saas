package com.example.Binary.Project.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String sku;
    private BigDecimal price;
    private Long companyId;
    private Long warehouseId;
    private Integer initialQuantity = 0;
}
