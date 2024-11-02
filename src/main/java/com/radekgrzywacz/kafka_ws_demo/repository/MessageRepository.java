package com.radekgrzywacz.kafka_ws_demo.repository;

import com.radekgrzywacz.kafka_ws_demo.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
