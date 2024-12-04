package br.upe.base.exceptions.usuarioExceptions;

public class EmailNaoCadastradoException extends RuntimeException {
    public EmailNaoCadastradoException(String email) {
        super("Não há nenhum usuário com email: " + email);
    }
}
