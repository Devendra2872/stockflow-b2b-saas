package com.example.Binary.Project.Repository;



import com.example.Binary.Project.DTO.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);
}

