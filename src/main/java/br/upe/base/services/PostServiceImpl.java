package br.upe.base.services;

import br.upe.base.models.Post;
import br.upe.base.models.DTOs.PostCreationDTO;
import br.upe.base.models.DTOs.PostDTO;
import br.upe.base.models.Usuario;
import br.upe.base.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public PostDTO createPost(PostCreationDTO postCreationDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste");
        usuario.setNome("melissa");
        usuario.setSenha("123");
        Usuario savedUsuario = usuarioService.salvarUsuario(usuario);

        Post post = new Post(
                null,
                savedUsuario,
                new HashSet<>(),
                postCreationDTO.titulo(),
                postCreationDTO.conteudo(),
                0,
                postCreationDTO.hashTags(),
                Instant.now()
        );

        Post savedPost = postRepository.save(post);
        return PostDTO.from(savedPost);
    }

    @Transactional
    public PostDTO updatePost(UUID postId, PostDTO postDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado com id: " + postId));

        post.setTitulo(postDetails.titulo());
        post.setConteudo(postDetails.conteudo());
        post.setHashTags(postDetails.hashTags());

        Post updatedPost = postRepository.save(post);
        return PostDTO.from(updatedPost);
    }

    public PostDTO getPostById(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado com id: " + postId));

        return PostDTO.from(post);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePost(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado com id: " + postId));

        postRepository.delete(post);
    }

    @Transactional
    public PostDTO addHashtags(UUID postId, List<String> hashtags) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado com id: " + postId));

        hashtags.forEach(post::addHashTag);
        Post updatedPost = postRepository.save(post);

        return PostDTO.from(updatedPost);
    }

    @Transactional
    public PostDTO removeHashtags(UUID postId, List<String> hashtags) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado com id: " + postId));

        hashtags.forEach(post::deleteHashTag);
        Post updatedPost = postRepository.save(post);

        return PostDTO.from(updatedPost);
    }

    @Transactional
    public PostDTO addCurtida(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado com id: " + postId));

        post.setCurtidas(post.getCurtidas() + 1);
        Post updatedPost = postRepository.save(post);

        return PostDTO.from(updatedPost);
    }

    @Transactional
    public PostDTO removeCurtida(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado com id: " + postId));

        if (post.getCurtidas() <= 0) throw new RuntimeException("Curtidas não podem ser negativas");

        post.setCurtidas(post.getCurtidas() - 1);
        Post updatedPost = postRepository.save(post);

        return PostDTO.from(updatedPost);
    }
}
