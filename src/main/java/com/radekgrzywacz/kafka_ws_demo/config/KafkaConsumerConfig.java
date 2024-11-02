package com.radekgrzywacz.kafka_ws_demo.config;

import com.radekgrzywacz.kafka_ws_demo.entity.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.group-id-private}")
    private String groupIdPrivate;

    @Value(value = "${spring.kafka.group-id-group}")
    private String groupIdGroup;

    // Consumer Factory for Private Messages
    @Bean
    public ConsumerFactory<String, Message> privateConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(groupIdPrivate));
    }

    // Consumer Factory for Group Messages
    @Bean
    public ConsumerFactory<String, Message> groupConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(groupIdGroup));
    }

    private Map<String, Object> consumerProps(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Error-handling deserializer for better resilience in case of serialization issues
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.radekgrzywacz.kafka_ws_demo.entity");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }

    // Kafka Listener Container Factory for Private Messages
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> privateKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(privateConsumerFactory());
        return factory;
    }

    // Kafka Listener Container Factory for Group Messages
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> groupKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(groupConsumerFactory());
        return factory;
    }
}
