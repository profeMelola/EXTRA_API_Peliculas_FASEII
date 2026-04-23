package es.daw.extra_api_peliculas.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "distributors")
public class Distributor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private boolean active = true;


    /*
     * RELACIÓN ManyToOne → Country
     *
     * Muchas distribuidoras pueden pertenecer al mismo país.
     * La FK "country_id" vive en la tabla distributors (lado propietario).
     * Se usa LAZY para no cargar el país si no se necesita.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="country_id", nullable = false)
    Country country;

    /*
     * RELACIÓN OneToMany → Release  (lado inverso, solo para navegación)
     *
     * Una distribuidora puede tener múltiples estrenos (en distintas películas/países).
     * mappedBy apunta al campo "distributor" que hay dentro de Release.
     * CascadeType.ALL + orphanRemoval si los estrenos dependen del ciclo de vida
     * de la distribuidora; en este caso se omite cascade para mayor control.
     */
    @OneToMany(mappedBy = "distributor", fetch = FetchType.LAZY)
    private List<Release> releases = new ArrayList<>();

}