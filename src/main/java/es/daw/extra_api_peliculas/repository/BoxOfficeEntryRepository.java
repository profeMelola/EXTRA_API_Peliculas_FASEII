package es.daw.extra_api_peliculas.repository;

import es.daw.extra_api_peliculas.dto.report.TopGrossingMovieReport;
import es.daw.extra_api_peliculas.entity.BoxOfficeEntry;
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
    @Query("""



""")
    List<TopGrossingMovieReport> topGrossingMovies(
            @Param("genre") String genre,
            @Param("from") LocalDate from,
            @Param("to")    LocalDate to,
            Pageable pageable
    );
}
