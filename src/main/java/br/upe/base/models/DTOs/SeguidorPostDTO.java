package br.upe.base.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;


public record SeguidorPostDTO(
        UUID seguidorId,
        PostDTO post
) {
    
    public static SeguidorPostDTO from(UUID seguidorId, PostDTO post) {
        return new SeguidorPostDTO(
                seguidorId,
                post
        );
    }
    public static SeguidorPostDTO fromJson(String jsonString) throws IOException {
        // Cria um ObjectMapper do Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Realiza a conversão de JSON para SeguidorPostDTO
        return objectMapper.readValue(jsonString, SeguidorPostDTO.class);
    }
}