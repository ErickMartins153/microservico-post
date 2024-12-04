package br.upe.base.exceptions.usuarioExceptions;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String email) {
        super(email + ". Email já cadastrado");
    }
}
