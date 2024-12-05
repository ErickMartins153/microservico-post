package br.upe.base.config;

import br.upe.base.models.DTOs.SeguidorPostDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class PostDeserializer implements Deserializer<SeguidorPostDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Nenhuma configuração especial necessária
    }

    @Override
    public SeguidorPostDTO deserialize(String topic, byte[] data) {
        try {
            // Desserializando o byte[] para SeguidorPostDTO
            return objectMapper.readValue(data, SeguidorPostDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desserializar SeguidorPostDTO", e);
        }
    }

    @Override
    public void close() {
        // Fechar recursos, se necessário
    }
}
