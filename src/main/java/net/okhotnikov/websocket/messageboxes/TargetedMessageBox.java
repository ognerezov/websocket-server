package net.okhotnikov.websocket.messageboxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.exceptions.BadTargetException;
import net.okhotnikov.websocket.handler.SecurityFilter;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static net.okhotnikov.websocket.util.CommonMessages.badTarget;
import static net.okhotnikov.websocket.util.CommonMessages.forbidden;

public class TargetedMessageBox extends RoomMessageBox{

    public TargetedMessageBox(RoomService roomService, SecurityFilter securityFilter, ObjectMapper mapper) {
        super(roomService, securityFilter, mapper);
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        super.receive(message, sender);

        if(message.participants == null || message.participants.isEmpty()) {
            LOG.error(String.format("Message of type %s must have targets, user: %s ",message.type.toString() , sender.getId()));
            sender.sendMessage(new TextMessage(badTarget(mapper)));
            throw new BadTargetException();
        }
    }
}
