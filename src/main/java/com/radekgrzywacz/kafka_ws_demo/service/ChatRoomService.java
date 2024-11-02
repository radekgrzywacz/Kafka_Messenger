package com.radekgrzywacz.kafka_ws_demo.service;

import com.radekgrzywacz.kafka_ws_demo.entity.ChatRoom;
import com.radekgrzywacz.kafka_ws_demo.entity.ChatType;
import com.radekgrzywacz.kafka_ws_demo.entity.User;
import com.radekgrzywacz.kafka_ws_demo.repository.ChatRoomRepository;
import com.radekgrzywacz.kafka_ws_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<ChatRoom>> findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        List<ChatRoom> rooms = chatRoomRepository.findByParticipantsId(user.getId());

        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(rooms);
        }
    }

    public ResponseEntity<ChatRoom> createPrivate(String username1, String username2) {
        User user1 = userRepository.findByUsername(username1).orElseThrow();
        User user2 = userRepository.findByUsername(username2).orElseThrow();
        if(user1 == null || user2 == null) {
            return ResponseEntity.noContent().build();
        } else {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setParticipants(new ArrayList<>());
            chatRoom.getParticipants().add(user1);
            chatRoom.getParticipants().add(user2);
            chatRoom.setChatType(ChatType.PRIVATE);

            chatRoomRepository.save(chatRoom);

            return ResponseEntity.ok(chatRoom);
        }
    }



}
