package com.example.Binary.Project.Repository;

import com.example.Binary.Project.DTO.LowStockAlertDTO;
import com.example.Binary.Project.DTO.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface LowStockRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT new com.stockflow.dto.LowStockAlertDTO(
            p.id, p.name, p.sku,
            w.id, w.name,
            i.quantity,
            COALESCE(pt.threshold, p.defaultThreshold),
            s.id, s.name, s.contactEmail,
            COALESCE(SUM(oi.quantity), 0)
        )
        FROM Product p
        JOIN Inventory i ON i.product.id = p.id
        JOIN Warehouse w ON w.id = i.warehouse.id
        LEFT JOIN ProductThreshold pt ON pt.productId = p.id AND pt.warehouseId = w.id
        LEFT JOIN OrderItem oi ON oi.product.id = p.id AND oi.warehouse.id = w.id
            AND oi.order.companyId = :companyId AND oi.order.createdAt >= :lookback
        LEFT JOIN ProductSupplier ps ON ps.productId = p.id AND ps.preferred = true
        LEFT JOIN Supplier s ON s.id = ps.supplierId
        WHERE p.companyId = :companyId
        GROUP BY p.id, p.name, p.sku, w.id, w.name, i.quantity,
                 pt.threshold, s.id, s.name, s.contactEmail
        HAVING i.quantity <= COALESCE(pt.threshold, p.defaultThreshold)
    """)
    List<LowStockAlertDTO> findLowStockAlerts(
            @Param("companyId") Long companyId,
            @Param("lookback") Instant lookback);
}

