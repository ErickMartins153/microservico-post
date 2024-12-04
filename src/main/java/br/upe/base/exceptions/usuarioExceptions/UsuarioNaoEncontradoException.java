package br.upe.base.exceptions.usuarioExceptions;

import java.util.UUID;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(UUID id) {
        super("O usuário com id " + id + " não foi encontrado.");
    }
}


