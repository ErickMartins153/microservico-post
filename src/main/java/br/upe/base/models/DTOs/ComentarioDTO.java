package br.upe.base.models.DTOs;

import br.upe.base.models.Comentario;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record ComentarioDTO(
        @NotNull(message = "o id do comentário é obrigatório")
        UUID id,
        String conteudo,
        int curtidas,
        Instant dataPublicacao) {

        public static ComentarioDTO from(Comentario comentario) {
                return new ComentarioDTO(
                        comentario.getId(),
                        comentario.getConteudo(),
                        comentario.getCurtidas(),
                        comentario.getDataPublicacao()
                );
        }

        public static Comentario to(ComentarioDTO dto) {
                Comentario comentario = new Comentario();
                comentario.setId(dto.id);
                comentario.setConteudo(dto.conteudo);
                comentario.setCurtidas(dto.curtidas);
                comentario.setDataPublicacao(dto.dataPublicacao);
                return comentario;
        }
}
