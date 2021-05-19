package net.okhotnikov.websocket.handler;

import net.okhotnikov.websocket.model.GenericMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface  MessageBox {
    <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException;
}
