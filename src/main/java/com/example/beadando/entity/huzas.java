package com.example.beadando.entity;

import jakarta.persistence.*;
import lombok.Data; // Lombok generálja a Gettereket/Settereket

@Entity // Jelzi, hogy ez egy adatbázis tábla
@Data   // Lombok annotáció
@Table(name = "huzas") // A tábla neve az adatbázisban
public class Huzas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer ev;

    @Column(nullable = false)
    private Integer het;

}