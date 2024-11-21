package br.upe.base.models.DTOs;

import br.upe.base.models.Post;
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
        public void addHashTag(String hashTag) {
                hashTags.add(hashTag);
        }

        public void deleteHashTag(String hashTag) {
                hashTags.remove(hashTag);
        }

        public static PostDTO from(Post post) {
                return new PostDTO(
                        post.getId(),
                        post.getTitulo(),
                        post.getConteudo(),
                        post.getCurtidas(),
                        post.getHashTags(),
                        post.getDataPublicacao()
                );
        }

        // Método estático para converter de PostDTO para Post
        public static Post to(PostDTO postDTO) {
                Post post = new Post();
                post.setId(postDTO.id());
                post.setTitulo(postDTO.titulo());
                post.setConteudo(postDTO.conteudo());
                post.setCurtidas(postDTO.curtidas());
                post.setHashTags(postDTO.hashTags());
                post.setDataPublicacao(postDTO.dataPublicacao());
                return post;
        }
}
