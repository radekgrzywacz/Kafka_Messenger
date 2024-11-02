package com.radekgrzywacz.kafka_ws_demo.repository;

import com.radekgrzywacz.kafka_ws_demo.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    @Query("{ 'participantsId': { $all: [?0, ?1], $size: 2 } }")
    Optional<ChatRoom> findByParticipantsIds(String firstID, String secondID);

    List<ChatRoom> findByParticipantsId(String participantId);
}

