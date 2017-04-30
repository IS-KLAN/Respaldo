package com.klan.proyecto.jpa.excepciones;

public class EntidadExistenteException extends Exception {
    public EntidadExistenteException(String message, Throwable cause) {
        super(message, cause);
    }
    public EntidadExistenteException(String message) {
        super(message);
    }
}
