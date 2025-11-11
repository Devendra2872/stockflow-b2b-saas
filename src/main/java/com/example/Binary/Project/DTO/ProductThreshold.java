package com.example.Binary.Project.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product_thresholds")
@Data
@IdClass(ProductWarehouseKey.class)
public class ProductThreshold {
    @Id
    private Long productId;
    @Id
    private Long warehouseId;
    private Integer threshold;
}

