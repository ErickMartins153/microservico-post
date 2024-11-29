package br.upe.base.models.DTOs;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ComentarioCreationDTO(
        @NotNull(message = "o id do post é obrigatório")
        UUID idPost,
        @NotNull(message = "o id do dono é obrigatório")
        UUID idDono,
        @NotNull(message = "o comentario precisa de um conteúdo")
        String conteudo) {
}
