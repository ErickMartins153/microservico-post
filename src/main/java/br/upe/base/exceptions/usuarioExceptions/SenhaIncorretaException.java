package br.upe.base.exceptions.usuarioExceptions;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException() {
        super("Senha invalida.");
    }
}
