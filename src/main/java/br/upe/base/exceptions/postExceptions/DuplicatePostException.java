package br.upe.base.exceptions.postExceptions;

public class DuplicatePostException extends RuntimeException {
    public DuplicatePostException(String field, String value) {
        super("Já existe um Post com " + field + " igual a " + value + ".");
    }
}
