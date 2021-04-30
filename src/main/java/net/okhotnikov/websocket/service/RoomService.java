package net.okhotnikov.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.model.Participant;
import net.okhotnikov.websocket.model.Room;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class RoomService {
    private final Map<String, Room> rooms;
    private final ObjectMapper mapper;

    public RoomService(ObjectMapper mapper) {
        this.mapper = mapper;
        this.rooms = new HashMap<String, Room>();
    }

    public void enter(Participant participant) throws IOException {

        Room room;
        if(rooms.containsKey(participant.room)){
            room =rooms
                .get(participant.room);
            room
                .enter(participant);
        } else{
            room = Room.create(participant);
            rooms
                .put(participant.room, room);
        }
        String msg ="error";
        try {
            msg = mapper.writeValueAsString(room);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for(WebSocketSession session: room.sessions.values()){
            session.sendMessage(new TextMessage(msg));
        }
    }
}
