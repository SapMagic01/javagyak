package com.example.beadando.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "huzott")
public class Huzott {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer szam;

    // Kapcsolat a szülő (Huzas) felé
    // A @JoinColumn adja meg, hogy az adatbázisban mi a foreign key neve (huzasid)
    @ManyToOne
    @JoinColumn(name = "huzasid", nullable = false)
    private Huzas huzas;
}