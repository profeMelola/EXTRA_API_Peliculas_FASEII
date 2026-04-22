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

    // Pendiente completar relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    Country country;
}