package br.upe.base.models.DTOs;

import java.time.Instant;
import java.util.UUID;

public record ComentarioDTO(
        UUID postId,
        UUID donoId,
        String conteudo,
        int curtidas,
        Instant dataPublicacao) {
}
