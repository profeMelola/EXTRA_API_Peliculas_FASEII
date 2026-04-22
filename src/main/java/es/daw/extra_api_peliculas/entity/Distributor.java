package es.daw.extra_api_peliculas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "distributors")
public class Distributor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private boolean active = true;

    // Pendiente completar relaciones

    @ManyToOne(fetch = FetchType.LAZY)
    Country country;

}