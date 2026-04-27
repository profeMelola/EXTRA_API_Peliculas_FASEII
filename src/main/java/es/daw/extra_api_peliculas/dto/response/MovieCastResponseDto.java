package es.daw.extra_api_peliculas.dto.response;

import es.daw.extra_api_peliculas.enums.Genre;

/** Respuesta para Movie */
//    {
//            "movieId": 5,
//            "title": "Inception",
//            "releaseYear": 2010,
//            "genre": "SCI_FI",
//            "active": true,
//            "actorId": 12,
//            "actorName": "Leonardo DiCaprio"
//    }
public record MovieCastResponseDto(
        Long movieId,
        String title,
        int releaseYear,
        //String genre,
        Genre genre,
        boolean active,

        Long actorId,// ide el actor recién añadido (lo uso para montar el Location)
        String actorName


) {}