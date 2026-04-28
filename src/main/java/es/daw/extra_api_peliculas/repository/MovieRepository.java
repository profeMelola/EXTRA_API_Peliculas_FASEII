package es.daw.extra_api_peliculas.repository;

import es.daw.extra_api_peliculas.dto.report.TopGrossingMovieReport;
import es.daw.extra_api_peliculas.entity.Movie;
import es.daw.extra_api_peliculas.enums.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("""
    SELECT DISTINCT m FROM Movie m 
    JOIN FETCH m.movieCast mc
    JOIN FETCH mc.actor
        """)
    List<Movie> findAllWithCast();

    /**
     * Devuelve todas las películas aunque no tengan facturación todavía.
     *
     * Hemos cambiado la dirección del FROM. Ahora es Movie.
     *
     * Con LEFT JOIN si una película no tiene releases ni registros de taquilla, sigue apareciendo con null en esos campos.
     *
     * En el WHERE hemos añadidos la condición de que pueden ser nulos (r.id IS NULL + boe.id IS NULL)
     *
     *
     * @param genre
     * @param from
     * @param to
     * @param pageable
     * @return
     */
    @Query("""
        SELECT new es.daw.extra_api_peliculas.dto.report.TopGrossingMovieReport
        (
            m.id,
            m.title,
            m.genre,
            count(boe.id),
            sum(boe.screens),
            sum(boe.gross)
        )
        FROM Movie m
            LEFT JOIN m.releases r
            LEFT JOIN r.boxOfficeEntries boe
        WHERE m.active = true
          AND (r.id IS NULL OR r.active = true)
          AND (boe.id IS NULL OR boe.active = true)
          AND (:genre IS NULL OR m.genre = :genre)
          AND (:from  IS NULL OR boe.periodStart >= :from)
          AND (:to    IS NULL OR boe.periodEnd   <= :to)    
        GROUP BY m.id, m.title, m.genre
        ORDER BY SUM(boe.gross) DESC
        
    """)
    List<TopGrossingMovieReport> topGrossingMovies(
            //@Param("genre") String genre,
            @Param("genre") Genre genre,
            @Param("from") LocalDate from,
            @Param("to")    LocalDate to,
            Pageable pageable
    );

    // PENDIENTE!!!! Montar el report con Page!!!
    // USAMOS COUNTQUERY cuando devolvemos objeto PAGE...
    List<Movie> findByGenre(Genre genre);


}


