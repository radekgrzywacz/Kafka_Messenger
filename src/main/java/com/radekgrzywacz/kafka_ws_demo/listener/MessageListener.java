package com.radekgrzywacz.kafka_ws_demo.listener;

import com.radekgrzywacz.kafka_ws_demo.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "private", groupId = "private")
    public void listenPrivate(Message message) {
        System.out.println("Sending private message via Kafka");
        // Send to a specific user based on chatId, which could represent the user's ID in a private chat
        messagingTemplate.convertAndSend("/topic/private/" + message.getChatId(), message);
    }

    @KafkaListener(topics = "group", groupId = "group")
    public void listenGroup(Message message) {
        System.out.println("Sending group message via Kafka");
        // Send to a group chat, where chatId is the group identifier
        messagingTemplate.convertAndSend("/topic/group/" + message.getChatId(), message);
    }
}
