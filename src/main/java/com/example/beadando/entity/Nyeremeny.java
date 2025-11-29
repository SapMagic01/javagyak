package com.example.beadando.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "nyeremeny")
public class Nyeremeny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer talalat; // pl. 5
    private Integer darab;   // pl. 12 nyertes
    private Long ertek;      // pl. 1500000 Ft

    @ManyToOne
    @JoinColumn(name = "huzasid", nullable = false)
    private Huzas huzas;
}