package br.upe.base.services.post;

import br.upe.base.models.Post;
import br.upe.base.models.Usuario;
import br.upe.base.models.DTOs.PostCreationDTO;
import br.upe.base.models.DTOs.PostDTO;
import br.upe.base.repositories.PostRepository;
import br.upe.base.repositories.UsuarioRepository;
import br.upe.base.services.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public PostDTO createPost(PostCreationDTO postCreationDTO) {
        Usuario dono = usuarioRepository.findById(postCreationDTO.donoId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Post post = PostDTO.from(new PostDTO(
                null,
                dono.getId(),
                postCreationDTO.titulo(),
                postCreationDTO.conteudo(),
                0,
                postCreationDTO.hashTags(),
                Instant.now()
        ), dono);
        Post savedPost = postRepository.save(post);
        return PostDTO.to(savedPost);
    }

    @Transactional
    public PostDTO updatePost(UUID postId, PostDTO postDetails) {
        Post post = getPostEntityById(postId);
        post.setTitulo(postDetails.titulo());
        post.setConteudo(postDetails.conteudo());
        post.setHashTags(postDetails.hashTags());
        Post updatedPost = postRepository.save(post);
        return PostDTO.to(updatedPost);
    }

    public PostDTO getPostById(UUID postId) {
        Post post = getPostEntityById(postId);
        return PostDTO.to(post);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostDTO::to)
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
        return PostDTO.to(updatedPost);
    }

    @Transactional
    public PostDTO removeHashtags(UUID postId, List<String> hashtags) {
        Post post = getPostEntityById(postId);
        hashtags.forEach(post::deleteHashTag);
        Post updatedPost = postRepository.save(post);
        return PostDTO.to(updatedPost);
    }

    @Transactional
    public PostDTO addCurtida(UUID postId) {
        Post post = getPostEntityById(postId);
        post.setCurtidas(post.getCurtidas() + 1);
        Post updatedPost = postRepository.save(post);
        return PostDTO.to(updatedPost);
    }

    @Transactional
    public PostDTO removeCurtida(UUID postId) {
        Post post = getPostEntityById(postId);
        if (post.getCurtidas() > 0) {
            post.setCurtidas(post.getCurtidas() - 1);
        }
        Post updatedPost = postRepository.save(post);
        return PostDTO.to(updatedPost);
    }

    private Post getPostEntityById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));
    }
}
