package es.daw.extra_api_peliculas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// NUEVO!!!! MARCAMOS EL HTTPSTATUS CUANDO SE PRODUCE ESTA EXCEPCIÓN
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
