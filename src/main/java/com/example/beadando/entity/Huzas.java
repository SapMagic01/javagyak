package com.example.beadando.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Entity
@Data
@Table(name = "huzas")
public class Huzas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ev;
    private Integer het;

    // Kapcsolat: Egy húzáshoz több kihúzott szám tartozik
    // A "mappedBy" azt jelenti, hogy a másik oldalon (Huzott) a "huzas" mezőnél van a kapcsolat
    @OneToMany(mappedBy = "huzas", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Fontos, hogy ne legyen végtelen ciklus a logolásnál
    private List<Huzott> huzottSzamok;

    // Kapcsolat: Egy húzáshoz több nyeremény típus tartozik
    @OneToMany(mappedBy = "huzas", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Nyeremeny> nyeremenyek;
}