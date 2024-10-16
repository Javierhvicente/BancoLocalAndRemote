package org.example.users.errors;

public abstract class UserErrors {
    private final String message;
    public String getMessage(){
        return message;
    }

    public UserErrors(String message){
        this.message = message;
    }

    /**
     * Excepcion para nombres de usuario invalidos
     * @author Javier Hernandez, Yahya el hadri, Javier Ruiz, Alvaro herrero, Samuel Cortes, Raul Fernandez
     * @version 1.0
     */
    public static class NombreInvalido extends UserErrors{
        public NombreInvalido(String message){
            super("ERROR: " + message);
        }
    }

    /**
     * Excepcion para correos electronicos invalidos
     * @author Javier Hernandez, Yahya el hadri, Javier Ruiz, Alvaro herrero, Samuel Cortes, Raul Fernandez
     * @version 1.0
     */
    public static class EmailInvalido extends UserErrors{
        public EmailInvalido(String message){
            super("ERROR: " + message);
        }
    }
}
