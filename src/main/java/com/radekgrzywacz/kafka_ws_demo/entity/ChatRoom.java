package com.radekgrzywacz.kafka_ws_demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class ChatRoom {
    @Id
    private String id;
    private List<User> participants;
    private ChatType chatType;
}
