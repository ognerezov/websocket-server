package net.okhotnikov.websocket.messageboxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.exceptions.MessageException;
import net.okhotnikov.websocket.handler.SecurityFilter;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;

import static net.okhotnikov.websocket.util.CommonMessages.disconnected;
import static net.okhotnikov.websocket.util.CommonMessages.sent;

public class KickBox extends TargetedMessageBox{
    public KickBox(RoomService roomService, SecurityFilter securityFilter, ObjectMapper mapper) {
        super(roomService, securityFilter, mapper);
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        try {
            super.receive(message, sender);
        }catch (MessageException e){
            return;
        }

        TextMessage msg = new TextMessage(disconnected(mapper));
        message
                .participants
                .stream()
                .map(id->roomService.getParticipant(id))
                .filter(Objects::nonNull)
                .map(target -> target.session)
                .forEach(session -> {
                    try {
                        session.sendMessage(msg);
                        session.close();
                    } catch (IOException e) {
                        LOG.error("Error closing session: " + session.getId());
                    }
                });

        sender.sendMessage(new TextMessage(sent(mapper)));
    }
}
