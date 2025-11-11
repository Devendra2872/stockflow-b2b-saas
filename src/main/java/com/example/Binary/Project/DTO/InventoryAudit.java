package com.example.Binary.Project.DTO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "inventory_audit")
@Data
public class InventoryAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private Integer changeQuantity;
    private String reason;
    @CreationTimestamp
    private Instant createdAt;
}


