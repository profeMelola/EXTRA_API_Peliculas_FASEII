package es.daw.extra_api_peliculas.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 3)
    private String code;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private boolean active = true;


    //Un país tiene muchas distribuidoras → Country OneToMany Distributor
    @OneToMany(mappedBy = "country")
    private List<Distributor> distributors = new ArrayList<>();

    //Un país tiene muchos registros de taquilla → Country OneToMany BoxOfficeEntry
    @OneToMany(mappedBy = "country")
    private List<BoxOfficeEntry> boxOfficeEntries = new ArrayList<>();

    // ----------------
    /*
     * Country es una entidad de catálogo puro (lookup table). POCOS ENDPOINTS SOBRE ÉL
     * NO se mapean colecciones inversas (OneToMany) desde aquí
     * para mantenerla ligera y evitar cargas innecesarias.
     * distributor.getCountry() // ✅ funciona
     * country.getDistributors() // ❌ no existe -> // DistributorRepository -> List<Distributor> findByCountryId(Long countryId);
     *
     * Relaciones en las que participa (lado "uno"):
     *   - Distributor  → cada distribuidora tiene su país de origen
     *   - BoxOfficeEntry → cada registro de taquilla indica en qué país recaudó
     */

//    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
//    private List<Distributor> distributors = new ArrayList<>();
//
//    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
//    private List<BoxOfficeEntry> boxOfficeEntries = new ArrayList<>();



}
