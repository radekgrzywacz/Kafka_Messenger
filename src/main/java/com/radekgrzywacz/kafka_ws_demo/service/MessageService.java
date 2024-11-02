package com.radekgrzywacz.kafka_ws_demo.service;

import com.radekgrzywacz.kafka_ws_demo.entity.Message;
import com.radekgrzywacz.kafka_ws_demo.entity.Status;
import com.radekgrzywacz.kafka_ws_demo.repository.ChatRoomRepository;
import com.radekgrzywacz.kafka_ws_demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Message save(Message chatMessage) {
        chatMessage.setStatus(Status.RECEIVED);
        messageRepository.save(chatMessage);
        return chatMessage;
    }
}
