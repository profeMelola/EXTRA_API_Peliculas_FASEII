package es.daw.extra_api_peliculas.exception;

import es.daw.extra_api_peliculas.dto.ErrorResponseDto;
import es.daw.extra_api_peliculas.enums.Genre;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    // -------- DOS FORMAS DE VALIDAR UN ENDPOINT --------------------------
    // MethodArgumentNotValidException -> se lanza al validar las propiedades de un JSON (body del request)

    // MethodArgumentTypeMismatchException -> se lanza al validar un pathvariable o requestparam (url del request)

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map( fe -> fe.getField()+" : "+ fe.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    // -------------- POR PONER UN ENUMERADO -------------
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleEnumMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String valoresValidos = Arrays.stream(Genre.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        String message = "Valor inválido '%s' para el parámetro '%s'. Valores permitidos: %s"
                .formatted(ex.getValue(), ex.getName(), valoresValidos);

        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }


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
