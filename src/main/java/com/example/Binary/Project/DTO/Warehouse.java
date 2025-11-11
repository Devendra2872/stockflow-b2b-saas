package com.example.Binary.Project.DTO;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "warehouses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"company_id", "name"})
})
@Data
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}

