package es.daw.extra_api_peliculas.service;

import es.daw.extra_api_peliculas.dto.report.TopGrossingMovieReport;
import es.daw.extra_api_peliculas.repository.BoxOfficeEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Servicio de negocio para los reports de taquilla.
 *
 * Responsabilidades de esta capa:
 *   - Validar las reglas de negocio (independientemente del protocolo HTTP).
 *   - Centralizar la lógica para que pueda reutilizarse desde otros
 *     contextos (tests, jobs, otros controladores) sin depender de HTTP.
 *
 * Ya NO construye el Pageable — lo recibe montado desde el controlador.
 *
 * @Transactional(readOnly = true) → optimización para queries de solo lectura:
 *   - Hibernate no hace flush antes de ejecutar la query.
 *   - Algunos drivers/BD activan optimizaciones de rendimiento.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final BoxOfficeEntryRepository boolOfficeEntryRepository;

    public List<TopGrossingMovieReport> getTopGrossingMovies(

    ){

    }
}
