package es.daw.extra_api_peliculas.repository;

import es.daw.extra_api_peliculas.dto.report.TopGrossingMovieReport;
import es.daw.extra_api_peliculas.entity.BoxOfficeEntry;
import es.daw.extra_api_peliculas.enums.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BoxOfficeEntryRepository extends JpaRepository<BoxOfficeEntry, Long> {
//    public record TopGrossingMovieReport(
//            Long   movieId,
//            String title,
//            String genre,
//            Long   totalEntries,      // agregado: count ->número de registros de taquilla
//            Long   totalScreens,      // agregado: sum -> suma de pantallas acumuladas
//            BigDecimal totalGross     // agregado: sum -> recaudación total
//    ) {}

    // PENDIENTE!!! HACER LA PRUEBA EN VEZ DE CON LIST CON PAGE!!!! COUNTQUERY????? REPASARLO!!!! REVISAR LA CLÍNICA!!!

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
FROM BoxOfficeEntry boe
    JOIN boe.release r
    JOIN r.movie m
WHERE boe.active = true
  AND r.active   = true
  AND m.active   = true
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
}
