package com.example.Binary.Project.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "product_suppliers")
@Data
@IdClass(ProductSupplierId.class)
public class ProductSupplier {
    @Id
    private Long productId;
    @Id
    private Long supplierId;

    private Boolean preferred = false;
    private BigDecimal supplierPrice;
}