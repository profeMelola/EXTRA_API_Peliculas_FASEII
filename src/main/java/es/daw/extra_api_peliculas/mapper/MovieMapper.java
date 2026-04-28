package es.daw.extra_api_peliculas.mapper;

import es.daw.extra_api_peliculas.dto.response.ActorCastDto;
import es.daw.extra_api_peliculas.dto.response.MovieResponseDto;
import es.daw.extra_api_peliculas.dto.response.MovieWithCastDto;
import es.daw.extra_api_peliculas.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieMapper {

    // PENDIENTE!!!! optimizar el código y montar un método (toActorCastDtoList...) para no tener código duplicado que genera
    // la instancia de ActorCastDto
    public MovieWithCastDto toMovieWithCastDto(Movie movie){
        // Primero montar la lista de ActorCastDto
        List<ActorCastDto> actorCastDtos = movie.getMovieCast().stream()
                .map(mc -> new ActorCastDto(
                        mc.getActor().getId(),
                        mc.getActor().getStageName(),
                        mc.getCharacterName(),
                        mc.getScreenMinutes()
                ))
                .toList();
        // Después montar MovieWithCastDto
        // Devolver MovieWithCastDto
        return new MovieWithCastDto(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getGenre(),
                movie.isActive(),
                actorCastDtos
        );

    }

    public MovieResponseDto toResponseDto(Movie movie) {
        return new MovieResponseDto(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getGenre(),
                movie.isActive()
        );
    }

    public List<ActorCastDto> toActorCastDtos(Movie movie) {
        return movie.getMovieCast().stream()
                .map(mc -> new ActorCastDto(
                        mc.getActor().getId(),
                        mc.getActor().getStageName(),
                        mc.getCharacterName(),
                        mc.getScreenMinutes()
                ))
                .toList();
    }

}
