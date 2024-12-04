package br.upe.base.exceptions.postExceptions;

import java.util.UUID;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(UUID postId) {
        super("Post com ID " + postId + " não encontrado.");
    }
}

