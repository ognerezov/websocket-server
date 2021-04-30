package net.okhotnikov.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.okhotnikov.websocket.exceptions.WrongRoomException;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

public class Room {
    public String id;

    public Set<Participant> participants;

    @JsonIgnore
    public Map<String, WebSocketSession> sessions;

    public Room() {
    }

    public void enter(Participant participant){
        if (!id.equals(participant.room))
            throw new WrongRoomException();

        participants.add(participant);
        sessions.put(participant.getId(),participant.session);
    }

    public static Room create(Participant creator){
        Room room = new Room();

        room.id = creator.room;
        room.participants = new HashSet<>();
        room.participants.add(creator);
        room.sessions = new HashMap<>();
        room.sessions.put(creator.getId(),creator.session);

        return room;
    }

    public boolean exit(Participant participant) {
        participants.remove(participant);
        sessions.remove(participant.getId());
        return participants.isEmpty();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public Map<String, WebSocketSession> getSessions() {
        return sessions;
    }

    public void setSessions(Map<String, WebSocketSession> sessions) {
        this.sessions = sessions;
    }
}
