package es.daw.extra_api_peliculas.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "releases")
public class Release {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private boolean active = true;

    // Pendiente completar relaciones
}
