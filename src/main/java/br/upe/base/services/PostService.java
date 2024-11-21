package br.upe.base.services;

import br.upe.base.models.DTOs.PostCreationDTO;
import br.upe.base.models.DTOs.PostDTO;
import br.upe.base.models.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostDTO createPost(PostCreationDTO postCreationDTO);

    PostDTO updatePost(UUID postId, PostDTO postDetails);

    PostDTO getPostById(UUID postId);

    List<PostDTO> getAllPosts();

    void deletePost(UUID postId);

    PostDTO addHashtags(UUID postId, List<String> hashtags);

    PostDTO removeHashtags(UUID postId, List<String> hashtags);

    PostDTO addCurtida(UUID postId);

    PostDTO removeCurtida(UUID postId);

}
