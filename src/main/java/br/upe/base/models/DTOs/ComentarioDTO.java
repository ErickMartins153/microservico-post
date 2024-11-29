package br.upe.base.models.DTOs;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record ComentarioDTO(
        @NotNull(message = "o id do comentário é obrigatório")
        UUID id,
        String conteudo,
        int curtidas,
        Instant dataPublicacao) {
}
