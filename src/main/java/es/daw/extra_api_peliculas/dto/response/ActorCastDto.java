package es.daw.extra_api_peliculas.dto.response;

// Actor dentro del cast
public record ActorCastDto(
        Long actorId, // actor
        String stageName, //actor
        String characterName, // lo sacamos de movie_cast
        short screenMinutes // lo sacamos de movie_cast
) {}