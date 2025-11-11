package com.example.Binary.Project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LowStockAlertDTO {
    private Long productId;
    private String productName;
    private String sku;
    private Long warehouseId;
    private String warehouseName;
    private Integer currentStock;
    private Integer threshold;
    private Long supplierId;
    private String supplierName;
    private String supplierEmail;
    private Long quantitySoldLast30Days;
}

