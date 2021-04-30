package net.okhotnikov.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.socket.WebSocketSession;

import static net.okhotnikov.websocket.util.Literals.ENTER_MESSAGE;
import static net.okhotnikov.websocket.util.Literals.ENTER_MESSAGE_ANONYMOUS;

public class Participant {
    public String name;
    public String room;
    public boolean isAdmin;
    public WebSocketSession session;

    public Participant() {
    }

    public Participant(String name, String room, boolean isAdmin) {
        this.name = name;
        this.room = room;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @JsonIgnore
    public WebSocketSession getSession() {
        return session;
    }

    @JsonIgnore
    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public String getId(){
        return this.session == null ? null : this.session.getId();
    }

    @JsonIgnore
    public String enterMessage(){
        return name == null ? ENTER_MESSAGE_ANONYMOUS + room : name + ENTER_MESSAGE + room;
    }
}
