package es.daw.extra_api_peliculas.dto.response;

/** Respuesta para Movie */
public record MovieResponseDto(
        Long id,
        String title,
        int releaseYear,
        String genre,
        boolean active
) {}