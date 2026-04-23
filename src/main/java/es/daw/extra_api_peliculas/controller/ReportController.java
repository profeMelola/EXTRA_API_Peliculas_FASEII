package es.daw.extra_api_peliculas.controller;

import es.daw.extra_api_peliculas.dto.report.TopGrossingMovieReport;
import es.daw.extra_api_peliculas.repository.BoxOfficeEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final BoxOfficeEntryRepository boxOfficeEntryRepository;

    // Público — cualquiera puede consultar el report
    @GetMapping("/top-grossing")
    public List<TopGrossingMovieReport> topGrossing(


    ) {



    }
}