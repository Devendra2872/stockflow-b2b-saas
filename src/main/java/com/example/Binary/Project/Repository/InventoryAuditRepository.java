package com.example.Binary.Project.Repository;

import com.example.Binary.Project.DTO.InventoryAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAuditRepository extends JpaRepository<InventoryAudit, Long> {
}