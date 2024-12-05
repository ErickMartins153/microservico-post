package br.upe.base.models.DTOs;

import br.upe.base.models.Comentario;
import br.upe.base.models.Post;
import br.upe.base.models.Usuario;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public record PostDTO(
        @NotNull(message = "O id do post é obrigatório")
        UUID id,
        UUID donoId,
        String titulo,
        String conteudo,
        int curtidas,
        List<Comentario> comentarios,
        Set<String> hashTags,
        Instant dataPublicacao
) {
    public static PostDTO to(Post post) {
        return new PostDTO(
                post.getId(),
                post.getDono() != null ? post.getDono().getId() : null,
                post.getTitulo(),
                post.getConteudo(),
                post.getCurtidas(),
                post.getComentarios(),
                post.getHashTags(),
                post.getDataPublicacao()
        );
    }

    public static Post from(PostDTO postDTO, Usuario dono) {
        return new Post(
                postDTO.id(),
                dono,
                postDTO.titulo(),
                postDTO.conteudo(),
                postDTO.curtidas(),
                postDTO.comentarios(),
                postDTO.hashTags(),
                postDTO.dataPublicacao()
        );
    }
}
