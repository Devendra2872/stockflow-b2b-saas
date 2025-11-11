package com.example.Binary.Project.DTO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sku"})
})
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String sku;
    private BigDecimal price;
    private String productType;
    private Integer defaultThreshold = 10;

    @Column(name = "company_id")
    private Long companyId;

    @CreationTimestamp
    private Instant createdAt;
}

