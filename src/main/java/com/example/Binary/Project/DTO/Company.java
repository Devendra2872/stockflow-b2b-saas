package com.example.Binary.Project.DTO;

@Entity
@Table(name = "companies")
@Data
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}

