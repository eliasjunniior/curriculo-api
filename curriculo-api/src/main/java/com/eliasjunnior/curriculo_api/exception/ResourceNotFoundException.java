package com.eliasjunnior.curriculoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Faz com que o Spring retorne HTTP 404 Not Found automaticamente quando essa exceção é lançada
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message); // Passa a mensagem de erro para a classe pai (RuntimeException)
    }
}