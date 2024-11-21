package br.upe.base.services;

import br.upe.base.models.Post;
import br.upe.base.models.DTOs.PostCreationDTO;
import br.upe.base.models.DTOs.PostDTO;
import br.upe.base.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostDTO createPost(PostCreationDTO postCreationDTO) {
        Post post = new Post(
                null,
                postCreationDTO.donoId(),
                postCreationDTO.titulo(),
                postCreationDTO.conteudo(),
                0,
                postCreationDTO.hashTags(),
                Instant.now()
        );
        Post savedPost = postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Transactional
    public PostDTO updatePost(UUID postId, PostDTO postDetails) {
        Post post = getPostEntityById(postId);
        post.setTitulo(postDetails.titulo());
        post.setConteudo(postDetails.conteudo());
        post.setHashTags(postDetails.hashTags());
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    public PostDTO getPostById(UUID postId) {
        Post post = getPostEntityById(postId);
        return mapToDTO(post);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePost(UUID postId) {
        Post post = getPostEntityById(postId);
        postRepository.delete(post);
    }

    @Transactional
    public PostDTO addHashtags(UUID postId, List<String> hashtags) {
        Post post = getPostEntityById(postId);
        hashtags.forEach(post::addHashTag);
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Transactional
    public PostDTO removeHashtags(UUID postId, List<String> hashtags) {
        Post post = getPostEntityById(postId);
        hashtags.forEach(post::deleteHashTag);
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Transactional
    public PostDTO addCurtida(UUID postId) {
        Post post = getPostEntityById(postId);
        post.setCurtidas(post.getCurtidas() + 1);
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Transactional
    public PostDTO removeCurtida(UUID postId) {
        Post post = getPostEntityById(postId);
        if (post.getCurtidas() > 0) {
            post.setCurtidas(post.getCurtidas() - 1);
        }
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    private Post getPostEntityById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));
    }

    private PostDTO mapToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitulo(),
                post.getConteudo(),
                post.getCurtidas(),
                post.getHashTags(),
                post.getDataPublicacao()
        );
    }
}
