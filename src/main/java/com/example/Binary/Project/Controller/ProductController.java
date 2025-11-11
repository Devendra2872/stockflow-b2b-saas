package com.example.Binary.Project.Controller;

import com.example.Binary.Project.DTO.*;
import com.example.Binary.Project.Repository.InventoryAuditRepository;
import com.example.Binary.Project.Repository.InventoryRepository;
import com.example.Binary.Project.Repository.ProductRepository;
import com.example.Binary.Project.Repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryAuditRepository inventoryAuditRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
        // Validate required fields
        if (request.getName() == null || request.getSku() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Missing required fields: name or sku"));
        }

        // Ensure SKU uniqueness
        if (productRepository.existsBySku(request.getSku())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "SKU already exists"));
        }

        // Validate price
        BigDecimal price = request.getPrice() == null ? BigDecimal.ZERO : request.getPrice();
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Price must be non-negative"));
        }

        // Create product
        Product product = new Product();
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setPrice(price);
        product.setCompanyId(request.getCompanyId()); // multi-tenant scope
        productRepository.save(product);

        // If initial inventory provided
        if (request.getWarehouseId() != null) {
            Warehouse wh = warehouseRepository.findById(request.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Invalid warehouse ID"));

            // Upsert inventory
            Inventory inv = inventoryRepository
                    .findByProductIdAndWarehouseId(product.getId(), wh.getId())
                    .orElse(new Inventory(product, wh, 0));

            inv.setQuantity(request.getInitialQuantity());
            inventoryRepository.save(inv);

            // Add audit log
            InventoryAudit audit = new InventoryAudit();
            audit.setProduct(product);
            audit.setWarehouse(wh);
            audit.setChangeQuantity(request.getInitialQuantity());
            audit.setReason("initial_stock_on_product_create");
            inventoryAuditRepository.save(audit);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Product created", "product_id", product.getId()));
    }
}

