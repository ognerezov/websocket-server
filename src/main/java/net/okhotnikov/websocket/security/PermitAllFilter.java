package net.okhotnikov.websocket.security;

import net.okhotnikov.websocket.handler.SecurityFilter;
import net.okhotnikov.websocket.model.GenericMessage;
import org.springframework.web.socket.WebSocketSession;

public class PermitAllFilter implements SecurityFilter {
    @Override
    public <T> boolean filter(GenericMessage<T> message, WebSocketSession sender) {
        return true;
    }
}
