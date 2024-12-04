package br.upe.base.controllers;

import br.upe.base.config.PostConsumer;
import br.upe.base.models.DTOs.ComentarioDTO;
import br.upe.base.models.DTOs.PostCreationDTO;
import br.upe.base.models.DTOs.PostDTO;
import br.upe.base.models.DTOs.UsuarioDTO;
import br.upe.base.services.comentario.ComentarioService;
import br.upe.base.services.post.PostService;
import br.upe.base.services.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private KafkaTemplate kafkaTemplate;
    private PostService postService;
    private UsuarioService usuarioService;
    private final PostConsumer postConsumer;
    private ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostCreationDTO post) {
        PostDTO createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable UUID postId, @Valid @RequestBody PostDTO postDetails) {
        PostDTO updatedPost = postService.updatePost(postId, postDetails);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable UUID postId) {
        PostDTO post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{idPost}/comentarios")
    public ResponseEntity<List<ComentarioDTO>> getAllComentariosFromPost(@PathVariable UUID idPost) {
        List<ComentarioDTO> comentarios = comentarioService.listAllComentarios(idPost);
        return ResponseEntity.ok().body(comentarios);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable UUID postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{postId}/hashtags")
    public ResponseEntity<PostDTO> addHashtags(@PathVariable UUID postId, @RequestBody List<String> hashtags) {
        PostDTO updatedPost = postService.addHashtags(postId, hashtags);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/hashtags")
    public ResponseEntity<PostDTO> removeHashtags(@PathVariable UUID postId, @RequestBody List<String> hashtags) {
        PostDTO updatedPost = postService.removeHashtags(postId, hashtags);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @PostMapping("/{postId}/curtidas")
    public ResponseEntity addCurtida(@PathVariable UUID postId) {
        PostDTO updatedPost = postService.addCurtida(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{postId}/curtidas")
    public ResponseEntity removeCurtida(@PathVariable UUID postId) {
        PostDTO updatedPost = postService.removeCurtida(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{postId}/send")
    public ResponseEntity<String> sendPostToKafka(@PathVariable UUID postId) {
        PostDTO post = postService.getPostById(postId);
        List<UsuarioDTO> seguidores = usuarioService.listarSeguidores(post.donoId());
        seguidores.forEach(seguidor -> {
            kafkaTemplate.send("post", seguidor.id().toString(), post);
        });
        return ResponseEntity.ok("Post enviado para Kafka com sucesso");
    }

    @GetMapping("/seguidor/{seguidorId}")
    public ResponseEntity<List<String>> getPosts(@PathVariable UUID seguidorId) {
        List<String> posts = postConsumer.getPostsBySeguidorId(seguidorId);

        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

}