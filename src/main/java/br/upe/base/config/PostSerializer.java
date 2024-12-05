package br.upe.base.config;

import br.upe.base.models.DTOs.SeguidorPostDTO;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;

public class PostSerializer implements Serializer<SeguidorPostDTO> {

    private ObjectMapper objectMapper = new ObjectMapper()
    .findAndRegisterModules() // Auto-register all available modules like JavaTimeModule
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Disable writing dates as timestamps


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Any configuration can be handled here if needed
    }

    @Override
    public byte[] serialize(String topic, SeguidorPostDTO data) {
        try {
            System.out.println(data);
            System.out.println(topic);
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing SeguidorPostDTO", e);
        }
    }

    @Override
    public void close() {
        // Close any resources if necessary
    }
}
