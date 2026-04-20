package es.daw.extra_api_peliculas.service;

import es.daw.extra_api_peliculas.dto.request.MovieCastRequestDto;
import es.daw.extra_api_peliculas.dto.response.MovieCastResponseDto;
import es.daw.extra_api_peliculas.entity.Actor;
import es.daw.extra_api_peliculas.entity.Movie;
import es.daw.extra_api_peliculas.entity.MovieCast;
import es.daw.extra_api_peliculas.entity.MovieCastId;
import es.daw.extra_api_peliculas.exception.ConflictException;
import es.daw.extra_api_peliculas.exception.ResourceNotFoundException;
import es.daw.extra_api_peliculas.repository.ActorRepository;
import es.daw.extra_api_peliculas.repository.MovieCastRepository;
import es.daw.extra_api_peliculas.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MovieCastService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieCastRepository movieCastRepository;

//    public MovieCastService(MovieRepository movieRepository, ActorRepository actorRepository) {
//        this.movieRepository = movieRepository;
//        this.actorRepository = actorRepository;
//    }

    /**
     * Añade un actor a una película creando un nuevo MovieCast.
     *
     * @param movieId  id de la película (path variable)
     * @param dto      datos del cast recibidos en el body
     * @throws ResponseStatusException 404 si la película o el actor no existen
     * @throws ResponseStatusException 409 si el actor ya tiene un rol en esa película
     */
    @Transactional
    public void addActorToMovie(Long movieId, MovieCastRequestDto dto) {

//        El JSON de Spring es el de /error de Spring Boot, y puede variar entre versiones.
//        Con tu ErrorResponseDto tú decides exactamente qué campos tiene, cómo se llaman y en qué formato van.
//        ResponseStatusException está bien para prototipos rápidos.
//          Las excepciones propias hacen que la capa de servicio no sepa nada de HTTP.
//          Un servicio no debería conocer códigos de estado, eso es responsabilidad de la capa web.

        // 1. Buscar película
//        Movie movie = movieRepository.findById(movieId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Película no encontrada con id: " + movieId));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Película no encontrado con id: "+movieId
                ));

        // 2. Buscar actor
//        Actor actor = actorRepository.findById(dto.actorId())
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Actor no encontrado con id: " + dto.actorId()));
        Actor actor = actorRepository.findById(dto.actorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Actor no encontrado con id: " + dto.actorId()
                ));

        // 3. Comprobar duplicado (mismo actor en la misma película)
        MovieCastId castId = new MovieCastId(movieId, dto.actorId());
        boolean alreadyExists = movie.getMovieCast().stream()
                .anyMatch(mc -> mc.getId().equals(castId));

        // Necesita crear la excepción propia ConflictException
//        if (movieCastRepository.existsById(castId)) {
//            throw new ConflictException(
//                    "El actor '%s' ya tiene un rol en la película '%s'"
//                            .formatted(actor.getStageName(), movie.getTitle()));
//        }

//        if (alreadyExists) {
//            throw new ResponseStatusException(
//                    HttpStatus.CONFLICT, "El actor ya tiene un rol en esta película");
//        }
        if(alreadyExists){
            throw new ConflictException(
                    "El actor ya tiene un rol en esta película"
            );
        }

        // 4. Crear MovieCast y asociar
        MovieCast movieCast = new MovieCast();
        movieCast.setId(castId);
        movieCast.setActor(actor);
        movieCast.setCharacterName(dto.characterName());
        movieCast.setScreenMinutes(dto.screenMinutes());
        movieCast.setSalaryOverride(dto.salaryOverride());

        movie.addMovieCast(movieCast);

        // Se salva automáticamente el entity movie
        // Al ser cascade ALL, no es necesario guardar explícitamente movieCast
    }

    @Transactional
    public MovieCastResponseDto addActorToMovieAndReturnsCastDto(Long movieId, MovieCastRequestDto dto) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Película no encontrado con id: "+movieId
                ));

        Actor actor = actorRepository.findById(dto.actorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Actor no encontrado con id: " + dto.actorId()
                ));

        // 3. Comprobar duplicado (mismo actor en la misma película)
        MovieCastId castId = new MovieCastId(movieId, dto.actorId());
        boolean alreadyExists = movie.getMovieCast().stream()
                .anyMatch(mc -> mc.getId().equals(castId));

        if(alreadyExists){
            throw new ConflictException(
                    "El actor ya tiene un rol en esta película"
            );
        }

        // 4. Crear MovieCast y asociar
        MovieCast movieCast = new MovieCast();
        movieCast.setId(castId);
        movieCast.setActor(actor);
        movieCast.setCharacterName(dto.characterName());
        movieCast.setScreenMinutes(dto.screenMinutes());
        movieCast.setSalaryOverride(dto.salaryOverride());

        movie.addMovieCast(movieCast);

        return new MovieCastResponseDto(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getGenre(),
                movie.isActive(),
                actor.getId(),
                actor.getStageName()
        );
    }

}

