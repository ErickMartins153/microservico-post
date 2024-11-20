package br.upe.base.models.DTOs;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PostDTO(
        @NotNull(message = "O id do post é obrigatório")
        UUID id,
        String titulo,
        String conteudo,
        int curtidas,
        Set<String> hashTags,
        Instant dataPublicacao
) {
}
