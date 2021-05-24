package net.okhotnikov.websocket.model;

import java.util.Set;

public class BasicMessage {
    public MessageType type;
    public Set<String> participants;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Set<String> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<String> participants) {
        this.participants = participants;
    }
}
