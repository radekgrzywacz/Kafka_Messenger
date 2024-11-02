package com.radekgrzywacz.kafka_ws_demo.controllers;

import com.radekgrzywacz.kafka_ws_demo.dto.CreatePrivateDTO;
import com.radekgrzywacz.kafka_ws_demo.dto.CreateUserDto;
import com.radekgrzywacz.kafka_ws_demo.entity.ChatRoom;
import com.radekgrzywacz.kafka_ws_demo.entity.Message;
import com.radekgrzywacz.kafka_ws_demo.entity.User;
import com.radekgrzywacz.kafka_ws_demo.service.ChatRoomService;
import com.radekgrzywacz.kafka_ws_demo.service.MessageService;
import com.radekgrzywacz.kafka_ws_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> findChats(String username) {
        return chatRoomService.findByUsername(username);
    }

    @PostMapping("/{topic}/{chatId}")
    public ResponseEntity<Message> sendMessage(@PathVariable String topic, @PathVariable String chatId,
                                               @RequestBody Message message) {
        message.setChatId(chatId);
        message.setTimestamp(new Date().toString());

        try {
            messageService.save(message);
            kafkaTemplate.send(topic, message);
            System.out.println("Message sent to topic: " + topic + " and chatId: " + chatId + " : " + message.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(message);
    }

    @PostMapping("/rooms")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody CreatePrivateDTO request) {
        return chatRoomService.createPrivate(request.getUsername1(), request.getUsername2());
    }

    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@RequestBody CreateUserDto userDto) {
        return userService.save(userDto);
    }
}
