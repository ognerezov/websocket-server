package net.okhotnikov.websocket.handler;

import net.okhotnikov.websocket.model.GenericMessage;
import org.springframework.web.socket.WebSocketSession;

public interface SecurityFilter {
    <T> boolean filter(GenericMessage<T> message, WebSocketSession sender);
}
