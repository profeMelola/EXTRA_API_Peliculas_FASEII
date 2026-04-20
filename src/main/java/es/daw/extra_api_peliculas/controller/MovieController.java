package es.daw.extra_api_peliculas.controller;

import es.daw.extra_api_peliculas.dto.request.MovieCastRequestDto;
import es.daw.extra_api_peliculas.dto.response.MovieCastResponseDto;
import es.daw.extra_api_peliculas.service.MovieCastService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieCastService movieCastService;

    // NO PERMITIDO EN EL EXAMEN. USAMOS LOMBOK!!!
//    public MovieController(MovieCastService movieCastService) {
//        this.movieCastService = movieCastService;
//    }

    /**
     *
     * POST /movies/{id}/cast
     * Añade un actor a la película indicada.
     * Devuelve 201 Created si la operación se realiza correctamente.
     */
//    @ResponseStatus es una anotación declarativa que fija el código HTTP en tiempo de compilación.
//    Es estático: siempre devolverá ese código, pase lo que pase.
//    ResponseEntity es un objeto que construyes en tiempo de ejecución.
//    Te da control total sobre el código de estado, las cabeceras y el cuerpo de la respuesta.

    // NO USAR @ResponseStatus EN LOS ENDPOINTS DE UN CONTROLADOR REST!!!! NOOOOOOOOOOOOOOOOOOO
    @PostMapping("/{id}/cast-con-ResponseStatus")
    @ResponseStatus(HttpStatus.CREATED)
    public void addActorToMovie2(
            @PathVariable Long id,
            @Valid @RequestBody MovieCastRequestDto dto) {

        movieCastService.addActorToMovie(id, dto);
    }

    @PostMapping("/{id}/cast")
    public ResponseEntity<Void> addActorToMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieCastRequestDto dto) {

            movieCastService.addActorToMovie(id, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    // ------------------------------------------
    // PENDIENTE!!!!
    // Uso de Location: /movies/42/cast/7
    // Uso de Dto en el response

    // --- RESPONSE ----
    //    HTTP/1.1 201 Created
//    Location: /movies/5/cast/12
//    Content-Type: application/json
//
//    {
//            "movieId": 5,
//            "title": "Inception",
//            "releaseYear": 2010,
//            "genre": "SCI_FI",
//            "active": true,
//            "actorId": 12,
//            "actorName": "Leonardo DiCaprio"
//    }
    @PostMapping("/{id}/cast-complete")
    public ResponseEntity<MovieCastResponseDto> addActorToMovieCastComplete(
            @PathVariable Long id,
            @Valid @RequestBody MovieCastRequestDto dto
    ){
        MovieCastResponseDto castDto = movieCastService.addActorToMovieAndReturnsCastDto(id,dto);

        // NUEVO!!!!
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                // url -> dame el personaje que interpreta el actor con id {actorId} en la película con id{movieID}
                .replacePath("/movie/{movieID}/cast/{actorId}")
                .buildAndExpand(castDto.movieId(),castDto.actorId())
                .toUri();

        return ResponseEntity
                .created(location) //201 + Location
                .body(castDto); // body con datos de la película + datos del actor añadido como personaje
    }

    // ------------------

    // PENDIENTE!!!
    // GET http://localhost:8080/movie/2/cast/1

    // PENDIENTE!!!
    // PROBAR ENDPOINTS QUE BORRAN MOVIE Y ACTOR... verificar en BD la configuración de CASCADE en los entities.
    // Comprobar vía java que al borrar un actor, si participa en una película NO BORRARLO!!!
    // No usar CASCADE en Actor, sino impedir vía java que no se borre...


}

