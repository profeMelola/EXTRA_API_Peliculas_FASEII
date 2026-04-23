package es.daw.extra_api_peliculas.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "box_office_entries")
public class BoxOfficeEntry {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate periodStart;

    @Column(nullable = false)
    private LocalDate periodEnd;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal gross; //recaudación bruta...

    @Column(nullable = false)
    private int screens; //salas de cine (número de pantallas, pantalla == cine)

    @Column(nullable = false)
    private boolean active = true;

    /*
     * RELACIÓN ManyToOne → Country
     *
     * Indica el país donde se generó esta recaudación concreta.
     * Nótese que el Release ya tiene una distribuidora con su país de origen,
     * pero la recaudación puede registrarse en un país diferente al de la distribuidora
     * (p. ej. distribuidora francesa recaudando en Bélgica).
     * La FK "country_id" vive en esta tabla.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="country_id", nullable = false)
    Country country;

    /*
     * RELACIÓN ManyToOne → Release  (lado propietario)
     *
     * Cada registro de taquilla pertenece a un único estreno.
     * La FK "release_id" vive en esta tabla (box_office_entries).
     * Es el lado propietario de la relación OneToMany definida en Release.
     */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="release_id", nullable = false)
    Release release;
}