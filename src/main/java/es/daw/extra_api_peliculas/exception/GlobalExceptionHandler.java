package es.daw.extra_api_peliculas.exception;

import es.daw.extra_api_peliculas.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Inyectamos HttpServletRequest solo para poder llamar a request.getRequestURI() y meter la ruta en el ErrorResponseDto
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflict(
            ConflictException ex,
            HttpServletRequest request) {

        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // PENDIENTE!!!!     @ExceptionHandler(MethodArgumentNotValidException.class)
    // Queremos que devuelva Map<propiedad, valor>.... o un String??

    // ------------- Método auxiliar privado -----

    /**
     * Construye un objeto ResponseEntity que encapsula un ErrorResponseDto
     * @param status
     * @param message
     * @param request
     * @return
     */
    private ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status,
                                                           String message,
                                                           HttpServletRequest request) {

//        public record ErrorResponseDto(
//                LocalDateTime timestamp,
//                int status,
//                String error,      // Texto HTTP estándar: "Not Found", "Conflict", etc.
//                String message,    // Mensaje de negocio legible
//                String path        // URI que originó el error
//        ) {}
        ErrorResponseDto body = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);

    }

}
