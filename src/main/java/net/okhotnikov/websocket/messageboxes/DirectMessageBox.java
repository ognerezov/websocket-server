package net.okhotnikov.websocket.messageboxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.exceptions.MessageException;
import net.okhotnikov.websocket.handler.SecurityFilter;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.Participant;
import net.okhotnikov.websocket.model.Room;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;

import static net.okhotnikov.websocket.util.CommonMessages.sent;
import static net.okhotnikov.websocket.util.CommonUtils.messageOrError;

public class DirectMessageBox extends TargetedMessageBox{
    public DirectMessageBox(RoomService roomService, SecurityFilter securityFilter, ObjectMapper mapper) {
        super(roomService, securityFilter, mapper);
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        try {
            super.receive(message, sender);
        }catch (MessageException e){
            return;
        }

        message
                .participants
                .stream()
                .map(id->roomService.getParticipant(id))
                .filter(Objects::nonNull)
                .map(target -> target.session)
                .forEach(session -> {
                    try {
                        session.sendMessage(new TextMessage(messageOrError(mapper,message)));
                    } catch (IOException e) {
                        LOG.error("Exception sending message to: " + session.getId());
                    }
                });

        sender.sendMessage(new TextMessage(sent(mapper)));
    }
}
