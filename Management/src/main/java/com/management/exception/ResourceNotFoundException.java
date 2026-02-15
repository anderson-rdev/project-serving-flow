package com.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Exceção lançada quando um recurso não é encontrado no sistema. **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /** Cria uma nova exceção com a mensagem informada. **/
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /** Cria uma nova exceção com mensagem e causa raiz. **/
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
