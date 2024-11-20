package br.upe.base.models.DTOs;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record PostCreationDTO(
        @NotNull(message = "O id do dono do post é obrigatório")
        UUID donoId,
        @NotNull(message = "O titulo do post é obrigatório")
        String titulo,
        @NotNull(message = "O conteúdo do post é obrigatório")
        String conteudo,
        Set<String> hashTags
) {

}
