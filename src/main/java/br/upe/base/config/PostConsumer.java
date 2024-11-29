package br.upe.base.config;

import br.upe.base.models.DTOs.PostDTO;
import br.upe.base.services.UsuarioService;
import lombok.AllArgsConstructor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class PostConsumer {

    private final Map<UUID, List<PostDTO>> postCache = new ConcurrentHashMap<>();

   @KafkaListener(topics = "post", groupId = "post-group")
    public void consumePost(ConsumerRecord<String, PostDTO> record) {
        UUID seguidorId = UUID.fromString(record.key());
        PostDTO post = record.value();

        postCache.computeIfAbsent(seguidorId, k -> new ArrayList<>()).add(post);
        System.out.printf("Post [%s] armazenado para seguidor [%s]%n", post.titulo(), seguidorId);
    }

    public List<PostDTO> getPostsBySeguidorId(UUID seguidorId) {
        return postCache.getOrDefault(seguidorId, Collections.emptyList());
    }

}
