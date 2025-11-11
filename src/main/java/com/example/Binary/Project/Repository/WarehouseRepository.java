package com.example.Binary.Project.Repository;

import com.example.Binary.Project.DTO.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findByCompanyId(Long companyId);
}
