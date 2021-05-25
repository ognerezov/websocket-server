package net.okhotnikov.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.okhotnikov.websocket.model.in.ControlMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static net.okhotnikov.websocket.util.Literals.*;

public class Participant {
    public String name;
    public String room;
    public boolean admin;
    public String token;
    public WebSocketSession session;
    public Set<MessageType> authorities = new HashSet<>();

    public Participant() {
    }

    public Participant(String name, String room, boolean admin, String token) {
        this.name = name;
        this.room = room;
        this.admin = admin;
        this.token = token;
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

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        admin = admin;
    }

    @JsonIgnore
    public WebSocketSession getSession() {
        return session;
    }

    @JsonIgnore
    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    @JsonIgnore
    public String getToken() {
        return token;
    }

    @JsonIgnore
    public void setToken(String token) {
        this.token = token;
    }

    public String getId(){
        return this.session == null ? null : this.session.getId();
    }

    @JsonIgnore
    public String enterMessage(){
        return name == null ? ENTER_MESSAGE_ANONYMOUS + room : name + ENTER_MESSAGE + room;
    }

    @JsonIgnore
    public String exitMessage(){
        return (name == null ? "" : name ) + EXIT_MESSAGE + room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    public Set<MessageType> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<MessageType> authorities) {
        this.authorities = authorities;
    }

    @JsonIgnore
    public boolean hasRole(MessageType role){
        return authorities.contains(role);
    }

    @JsonIgnore
    public void addRole(MessageType role){
        authorities.add(role);
    }

    @JsonIgnore
    public void removeRole(MessageType role){
        authorities.remove(role);
    }

    @JsonIgnore
    public boolean hasPermissionFor(MessageType messageType){
        return  admin || hasRole(messageType);
    }

    @JsonIgnore
    public Participant apply(ControlMessage controlMessage){

        if (controlMessage.allow){
            authorities.addAll(controlMessage.getAuthorities());
        } else {
            authorities.removeAll(controlMessage.getAuthorities());
        }

        return this;
    }

    @JsonIgnore
    public boolean doesAcceptPetitions(){
        return admin || authorities.contains(MessageType.Control);
    }
}
