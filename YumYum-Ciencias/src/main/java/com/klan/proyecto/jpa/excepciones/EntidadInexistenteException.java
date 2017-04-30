package com.klan.proyecto.jpa.excepciones;

public class EntidadInexistenteException extends Exception {
    public EntidadInexistenteException(String message, Throwable cause) {
        super(message, cause);
    }
    public EntidadInexistenteException(String message) {
        super(message);
    }
}
