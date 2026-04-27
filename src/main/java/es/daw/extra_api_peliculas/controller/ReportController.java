package es.daw.extra_api_peliculas.controller;

import es.daw.extra_api_peliculas.dto.report.TopGrossingMovieReport;
import es.daw.extra_api_peliculas.entity.BoxOfficeEntry;
import es.daw.extra_api_peliculas.repository.BoxOfficeEntryRepository;
import es.daw.extra_api_peliculas.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
//@Validated -> para validaciones de @RequestParam
//    Sin @Validated, @Pattern en un @RequestParam se ignora silenciosamente.
//    Y la excepción que se lanza en este caso no es MethodArgumentNotValidException (????)
//    sino ConstraintViolationException,
//    así que necesitarías otro handler en tu GlobalExceptionHandler

// En la refactorización con el enumerado en género: MethodArgumentTypeMismatchException (propiedades json)

public class ReportController {

    private final ReportService reportService;

    // Público — cualquiera puede consultar el report
    @GetMapping("/top-grossing")
    public ResponseEntity<List<TopGrossingMovieReport>> topGrossing(

            @RequestParam(required = false)
            // PENDIENTE!!! validar por @pattern que lleva un valor correcto de los permitidos del enumerado
            String genre,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to,

            @PageableDefault(page = 0, size = 10)
            Pageable pageable

            ) {

            List<TopGrossingMovieReport> result = reportService.getTopGrossingMovies(
                    genre, from, to, pageable, false
            );

            return ResponseEntity.ok(result);


    }

    @GetMapping("/top-movies-grossing")
    public ResponseEntity<List<TopGrossingMovieReport>> topGrossingMovies(

            @RequestParam(required = false)
            String genre,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to,

            @PageableDefault(page = 0, size = 10)
            Pageable pageable

    ) {

        List<TopGrossingMovieReport> result = reportService.getTopGrossingMovies(
                genre, from, to, pageable, true
        );

        return ResponseEntity.ok(result);


    }

}