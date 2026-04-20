package es.daw.extra_api_peliculas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// NUEVO!!!! MARCAMOS EL HTTPSTATUS CUANDO SE PRODUCE ESTA EXCEPCIÓN
/*
Cuando tienes un @ExceptionHandler para una excepción, @ResponseStatus en la clase de la excepción es ignorado.
Spring da prioridad al handler.
Entonces, ¿para qué ponerlo? Por documentación y seguridad:

Deja claro semánticamente qué código HTTP representa esa excepción.
Si algún día eliminas el handler por error, Spring usa @ResponseStatus como fallback y no devuelve un 500.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{

    public ConflictException(String message) {
        super(message);
    }

}
