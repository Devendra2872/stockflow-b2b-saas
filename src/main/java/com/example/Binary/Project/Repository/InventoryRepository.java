package com.example.Binary.Project.Repository;

import com.example.Binary.Project.DTO.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductIdAndWarehouseId(Long productId, Long warehouseId);
    List<Inventory> findByWarehouseIdIn(List<Long> warehouseIds);
    List<Inventory> findByProductIdIn(List<Long> productIds);
}
