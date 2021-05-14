package net.okhotnikov.websocket.model;

public class BasicMessage {
    public MessageType type;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
