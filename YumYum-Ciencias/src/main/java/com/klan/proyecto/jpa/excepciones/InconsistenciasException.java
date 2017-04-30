package com.klan.proyecto.jpa.excepciones;

import java.util.ArrayList;
import java.util.List;

public class InconsistenciasException extends Exception {
    private List<String> messages;
    public InconsistenciasException(List<String> messages) {
        super((messages != null && messages.size() > 0 ? messages.get(0) : null));
        if (messages == null) {
            this.messages = new ArrayList<String>();
        }
        else {
            this.messages = messages;
        }
    }
    public List<String> getMessages() {
        return messages;
    }
}
