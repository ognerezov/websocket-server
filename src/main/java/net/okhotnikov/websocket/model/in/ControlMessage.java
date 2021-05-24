package net.okhotnikov.websocket.model.in;

import net.okhotnikov.websocket.model.MessageType;

import java.util.Set;

public class ControlMessage {
    public boolean allow;
    public Set<MessageType> authorities;

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    public Set<MessageType> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<MessageType> authorities) {
        this.authorities = authorities;
    }
}
