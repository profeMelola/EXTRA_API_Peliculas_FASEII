package es.daw.extra_api_peliculas.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

/**
 * Entidad que representa un actor. LADO INVERSO DE LA RELACIÓN MANY2MANY
 *
 * Relación con MovieCast:
 *   - Un actor puede tener varios roles en distintas películas: @OneToMany
 *   - No tiene cascade ni orphanRemoval porque el ciclo de vida de un rol
 *     lo gestiona Movie (el recurso raíz en la API). MOVIE ES EL GESTOR
 *      Se elige como "gestor" del ciclo de vida el lado que semánticamente contiene al otro en el dominio.
 *      Un reparto (cast) pertenece a una película, no al revés,
 *      por lo que es más natural que Movie sea quien gestione la colección.
 *      Además, los endpoints están orientados a la película (/movies/{id}/cast), lo que refuerza esa decisión.
 *      Técnicamente podrías poner cascade en cualquier lado inverso.
 *         La razón real es que semánticamente un actor no debería ser responsable de gestionar sus personajes o roles:
 *              es la película la que tiene un reparto, no el actor el que "posee" sus participaciones.
 */
@Entity
@Table(name = "actors")
@Getter
public class Actor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String stageName;

    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(length = 60)
    private String nationality;

    @Column(nullable = false)
    private boolean active = true;

    // Pendiente!!!! pongo cascade all y orphanRemoval????

    /**
     * Colección de roles(personajes) en los que participa este actor.
     *
     * - mappedBy = "actor": lado inverso.
     * - Sin cascade: el actor no gestiona el ciclo de vida de sus personajes.
     *   Si se borra un actor, no se eliminan en cascada sus MovieCast????????????
     *      Si intentas borrar un actor que tiene filas en movie_cast,
     *      la base de datos lanzará un error de violación de FK porque actor_id es parte de la PK compuesta y hay filas que lo referencian.
     *
     *      ¿Cómo lo soluciono?
     *          // Opción A: borrado en cascada (si se borra el actor, se borran sus roles)
     *          @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
     *
     *          // PENDIENTE!!! IMPLEMENTAR ENDPOINT DE BORRAR ACTOR Y GESTIONAR PREVIAMENTE SI ES PERSONAJE
     *          // DE ALGUNA PELÍCULA. EN CASO DE QUE ACTUE EN PELIS, DEVOLVEMOS MENSAJE: el actor participa en películas y no se puede borrar.
     *          // Y ADEMÁS UN JSON CON LA INFORMACIÓN DE LAS PELIS EN LAS QUE PARTICIPA Y EL PERSONAJE QUE INTERPRETA
     *
     *          // Opción B: no permitir borrar un actor con roles activos → lanzar excepción en el servicio
     *          if (!actor.getCast().isEmpty()) {
     *              throw new ConflictException("No se puede eliminar un actor con roles activos");}
     */
    @OneToMany(mappedBy = "actor")
    private Set<MovieCast>  movieCast;

    // ---------- el helpers bidireccionales ------------
    public void addMovieCast(MovieCast movieCast){
        this.movieCast.add(movieCast);
        movieCast.setActor(this);
    }

    public void removeMovieCast(MovieCast movieCast){
        this.movieCast.remove(movieCast);
        movieCast.setActor(null);
    }

}