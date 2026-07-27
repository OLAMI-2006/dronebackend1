package com.example.dronemanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medications")
@Data
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // name of drugs, vaccines, or blood bag

    @Column(nullable = false)
    private Double weight; // weight in kilograms

    @Column(unique = true, nullable = false)
    private String code; // identification code

    @Column(nullable = false)
    private Double quantity; // Inventory stock count at the hub


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id")
    private Drone drone;

    public void setType(String blood) {
    }


}
