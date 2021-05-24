package net.okhotnikov.websocket.messageboxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.exceptions.ForbiddenException;
import net.okhotnikov.websocket.exceptions.MessageException;
import net.okhotnikov.websocket.handler.SecurityFilter;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.MessageType;
import net.okhotnikov.websocket.model.Participant;
import net.okhotnikov.websocket.model.Room;
import net.okhotnikov.websocket.model.in.ControlMessage;
import net.okhotnikov.websocket.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;

import static net.okhotnikov.websocket.util.CommonMessages.badTarget;
import static net.okhotnikov.websocket.util.CommonMessages.invalidControlMessage;

public class ControlMessageBox extends TargetedMessageBox{
    private static final Logger LOG = LogManager.getLogger(ControlMessageBox.class);

    public ControlMessageBox(RoomService roomService, SecurityFilter securityFilter, ObjectMapper mapper) {
        super(roomService, securityFilter, mapper);
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        try {
            super.receive(message, sender);
        }catch (MessageException e){
            return;
        }

        ControlMessage controlMessage = mapper.convertValue(message.data,ControlMessage.class);

        if (controlMessage == null){
            LOG.error("Invalid control message from user: " + sender.getId());
            sender.sendMessage(new TextMessage(invalidControlMessage(mapper)));
            return;
        }

        Participant participant = roomService.getParticipant(sender.getId());
        Room room = roomService.getRoom(participant);

        message
                .participants
                .stream()
                .map(id->roomService.getParticipant(id))
                .filter(Objects::nonNull)
                .forEach(target -> target.apply(controlMessage));

        roomService.send(MessageType.RolesApplied,room);
    }
}
