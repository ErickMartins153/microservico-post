package br.upe.base.config;

import br.upe.base.models.DTOs.PostDTO;
import br.upe.base.models.DTOs.SeguidorPostDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class PostConsumer {

    private final Map<UUID, List<SeguidorPostDTO>> postCache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public PostConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper; // Recebendo o ObjectMapper configurado
    }

    @KafkaListener(topics = "post", groupId = "post-group")
    public void consumePost(ConsumerRecord<String, String> record) {
        try {
            SeguidorPostDTO post = objectMapper.readValue(record.value(), SeguidorPostDTO.class);
            UUID seguidorId = UUID.fromString(record.key());
            postCache.computeIfAbsent(seguidorId, k -> new ArrayList<>()).add(post);
        } catch (IOException e) {
            System.err.println("Erro ao converter JSON para SeguidorPostDTO: " + e.getMessage());
        }
    }

    public List<PostDTO> getPostsBySeguidorId(UUID seguidorId) {
        // Filtra todos os posts do cache que pertencem ao seguidor
        List<SeguidorPostDTO> posts = postCache.values().stream()
                .flatMap(List::stream)  // Flatten a lista de listas para uma lista única de SeguidorPostDTO
                .filter(post -> post.seguidorId().equals(seguidorId))  // Filtra pelo seguidorId
                .collect(Collectors.toList());

        if (posts.isEmpty()) {
            return Collections.emptyList(); // Retorna uma lista vazia se não houver posts
        }

        // Extrai os objetos PostDTO de cada SeguidorPostDTO
        return posts.stream()
                    .map(SeguidorPostDTO::post)  // Mapear cada SeguidorPostDTO para PostDTO
                    .collect(Collectors.toList());
    }
}

