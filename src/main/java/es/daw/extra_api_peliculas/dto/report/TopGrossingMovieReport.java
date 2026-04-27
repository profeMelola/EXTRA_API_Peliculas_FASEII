package es.daw.extra_api_peliculas.dto.report;

import es.daw.extra_api_peliculas.enums.Genre;

import java.math.BigDecimal;

// Clase inmutable!!! una vez creado el objeto no puedo cambiar el valor de sus propiedades
public record TopGrossingMovieReport(
        Long   movieId,
        String title,
        //String genre,
        Genre genre,
        Long   totalEntries,      // número de registros de taquilla
        Long   totalScreens,      // suma de pantallas (salas) acumuladas
        BigDecimal totalGross     // recaudación total (bruto)
) {
    public TopGrossingMovieReport{
        if (totalGross == null) totalGross = BigDecimal.ZERO;
        if (totalScreens == null) totalScreens = 0L;
    }
}