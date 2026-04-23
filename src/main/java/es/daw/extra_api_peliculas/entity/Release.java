package es.daw.extra_api_peliculas.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "releases")
public class Release {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private boolean active = true;


    /*
     * RELACIÓN OneToMany → BoxOfficeEntry  (lado inverso)
     *
     * Un estreno acumula múltiples registros de taquilla a lo largo del tiempo
     * (cada uno cubre un periodo semanal o diario).
     * mappedBy apunta al campo "release" dentro de BoxOfficeEntry.
     * CascadeType.ALL + orphanRemoval = true porque los registros de taquilla
     * no tienen sentido sin su estreno: si se borra el Release, se borran sus entradas.
     */
    @OneToMany(
            mappedBy = "release",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BoxOfficeEntry> boxOfficeEntries = new ArrayList<>();

    /*
     * RELACIÓN ManyToOne → Distributor
     *
     * Cada estreno es gestionado por una única distribuidora.
     * La FK "distributor_id" vive en la tabla releases.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "distributor_id", nullable = false)
    private Distributor distributor;


    /*
     * RELACIÓN ManyToOne → Movie  (entidad ya existente en Fase I)
     *
     * Muchos estrenos pueden corresponder a la misma película
     * (p. ej. Inception se estrena en USA y en España = 2 releases).
     * La FK "movie_id" vive en la tabla releases.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;


}
