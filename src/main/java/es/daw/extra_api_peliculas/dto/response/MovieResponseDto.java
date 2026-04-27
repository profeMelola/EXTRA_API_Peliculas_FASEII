package es.daw.extra_api_peliculas.dto.response;

import es.daw.extra_api_peliculas.enums.Genre;

/** Respuesta para Movie */
public record MovieResponseDto(
        Long id,
        String title,
        int releaseYear,
        //String genre,
        Genre genre,
        boolean active
) {}