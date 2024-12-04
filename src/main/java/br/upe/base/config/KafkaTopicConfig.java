package br.upe.base.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {


    @Bean
    public NewTopic postCreatedTopic() {
        return TopicBuilder.name("post")
                .partitions(1)
                .build();
    }

}