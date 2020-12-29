package com.perso.back.task_planner.exception;

public class CustomMappingException extends Exception {

    private static final String message = "An error during the data mapping happened";

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Construit une nouvelle exception sans message.
     */
    public CustomMappingException() {
        super();
    }

    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message le message à afficher lors du lancement de l'erreur
     */
    public CustomMappingException(String message) {
        super(message);
    }
}
