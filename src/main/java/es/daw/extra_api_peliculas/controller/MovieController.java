package es.daw.extra_api_peliculas.controller;

import es.daw.extra_api_peliculas.dto.request.MovieCastRequestDto;
import es.daw.extra_api_peliculas.dto.response.MovieCastResponseDto;
import es.daw.extra_api_peliculas.dto.response.MovieResponseDto;
import es.daw.extra_api_peliculas.dto.response.MovieWithCastDto;
import es.daw.extra_api_peliculas.enums.Genre;
import es.daw.extra_api_peliculas.service.MovieCastService;
import es.daw.extra_api_peliculas.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieCastService movieCastService;
    private final MovieService movieService;

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

    // GET http://localhost:8080/movie/2/cast/1
    @GetMapping("/{movieId}/cast/{actorId}")
    public ResponseEntity<MovieCastResponseDto> getMovieCast(
            @PathVariable Long movieId,
            @PathVariable Long actorId) {

        MovieCastResponseDto dto = movieCastService.getMovieCast(movieId, actorId);
        return ResponseEntity.ok(dto);
    }


    // PROBAR ENDPOINTS QUE BORRAN MOVIE Y ACTOR... verificar en BD la configuración de CASCADE en los entities.
    // Comprobar vía java que al borrar un actor, si participa en una película NO BORRARLO!!!
    // No usar CASCADE en Actor, sino impedir vía java que no se borre...
    // Nuevo endpoint: borrar película (debe eliminar también MovieCast asociadas por cascade)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieCastService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    // obtener todas las películas con su casting completo
    // https://github.com/profeMelola/DWES-REFUERZO-EXTRAORDINARIA/tree/main/SPRING/Peliculas#ampliaci%C3%B3n--obtener-todas-las-pel%C3%ADculas-con-su-casting-completo

    @GetMapping("/with-cast")
    public ResponseEntity<List<MovieWithCastDto>> getMovieCastWithCast(){
        return ResponseEntity.ok(movieService.getAllMoviesWithCast());

    }

    // ------------------ PARA PROBAR EL ENUMERADO ------------
    // Spring convierte automáticamente el String del query param en Enum (Jackson).
    // Si llega valor desconocido (uno que no coincide con los valores del Enum) -> devolver un 400 (Bad Request), sin código adicional.
    // Lanzaría la excepcion MethodArgumentTypeMismatchException (ok?)

    // Si quisiéramos usar un @Pattern para validar los géneros que llegan al endpoint, no podrías usar Genre (el enumerado).
    // Las constrains como @Pattern solo funcionan con String.
//    @RequestParam(required = false)
//    @Pattern(regexp = "^(ACTION|SCI_FI|DRAMA|WAR)$", message = "Género inválido")
//    String genre

    // @Validates -> imprescindible para que valide @RequestParam y @PathVariable (????)
//    Sin @Validated, @Pattern en un @RequestParam se ignora silenciosamente.
//    Y la excepción que se lanza en este caso no es MethodArgumentNotValidException sino ConstraintViolationException,
//    así que necesitarías otro handler en tu GlobalExceptionHandler



    // PENDIENTE!!!! HACER PRUEBAS VALIDANDO QUE LLEGA UN GÉNERO CORRECTO!!!!
    // GESTIONAR MethodArgumentTypeMismatchException!!!!
    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getMovies(
            @RequestParam(required = false)Genre genre ){
        // PENDIENTE!!!
        return null;
    }


}

