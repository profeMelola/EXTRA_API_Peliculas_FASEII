package es.daw.extra_api_peliculas.dto.response;

import es.daw.extra_api_peliculas.enums.Genre;

import java.util.List;

// Película con su lista de actores
public record MovieWithCastDto(
        Long id,
        String title,
        int releaseYear,
        //String genre,
        Genre genre,
        boolean active,
        List<ActorCastDto> cast
) {}
