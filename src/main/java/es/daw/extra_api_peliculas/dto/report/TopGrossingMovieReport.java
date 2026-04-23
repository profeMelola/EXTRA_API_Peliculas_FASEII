package es.daw.extra_api_peliculas.dto.report;

import java.math.BigDecimal;

public record TopGrossingMovieReport(
        Long   movieId,
        String title,
        String genre,
        Long   totalEntries,      // número de registros de taquilla
        Long   totalScreens,      // suma de pantallas (salas) acumuladas
        BigDecimal totalGross     // recaudación total (bruto)
) {}