package net.okhotnikov.websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.MessageType;
import net.okhotnikov.websocket.model.Participant;
import net.okhotnikov.websocket.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static net.okhotnikov.websocket.util.Literals.*;
import static net.okhotnikov.websocket.util.CommonUtils.*;

@Service
public class RoomService {
    private final Map<String, Room> rooms;
    private final Map<String,Participant> participants;
    private final ObjectMapper mapper;

    private static final Logger LOG = LogManager.getLogger(RoomService.class);

    public RoomService(ObjectMapper mapper) {
        this.mapper = mapper;
        this.rooms = new HashMap<>();
        this.participants = new HashMap<>();
    }

    public void enter(Participant participant) throws IOException {

        Room room;
        if(rooms.containsKey(participant.room)){
            room = getRoom(participant);
            room
                .enter(participant);
        } else{
            room = Room.create(participant);
            rooms
                .put(participant.room, room);
        }

        participants.put(participant.getId(),participant);
        send(MessageType.ParticipantEnter,room);
    }

    public void send(MessageType type, Room room) throws IOException {
        String msg = messageOrError(mapper,new GenericMessage<>(type, room));
        for(WebSocketSession session: room.sessions.values()){
            session.sendMessage(new TextMessage(msg));
        }
    }

    public Room getRoom(Participant participant) {
        return rooms.get(participant.room);
    }


    public Participant getParticipant(String id) {
        return participants.get(id);
    }

    public void exit(String sessionId) throws IOException{
        Participant participant = participants.remove(sessionId);
        if (participant == null){
            LOG.error(NO_PARTICIPANT_ON_DISCONNECT);
            return;
        }

        Room room = getRoom(participant);

        if (room == null){
            LOG.error(NO_ROOM_PARTICIPANT_ON_DISCONNECT);
            return;
        }

        LOG.info(participant.exitMessage());

        if (room.exit(participant)){
            LOG.info(ROOM_IS_EMPTY);
            rooms.remove(room.id);
        }

        send(MessageType.ParticipantExit,room);

    }
}
