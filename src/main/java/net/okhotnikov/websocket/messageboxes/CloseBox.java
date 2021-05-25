package net.okhotnikov.websocket.messageboxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.exceptions.MessageException;
import net.okhotnikov.websocket.handler.SecurityFilter;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.Room;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static net.okhotnikov.websocket.util.CommonMessages.disconnected;
import static net.okhotnikov.websocket.util.CommonMessages.sent;

public class CloseBox extends RoomMessageBox{
    public CloseBox(RoomService roomService, SecurityFilter securityFilter, ObjectMapper mapper) {
        super(roomService, securityFilter, mapper);
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        try {
            super.receive(message, sender);
        }catch (MessageException e){
            return;
        }

        Room room = roomService.getRoom(roomService.getParticipant(sender.getId()));

        TextMessage msg = new TextMessage(disconnected(mapper));
        room
                .participants
                .stream()
                .map(participant -> participant.session)
                .filter(session -> !sender.getId().equals(session.getId()))
                .forEach(session -> {
                    try {
                        session.sendMessage(msg);
                        roomService.remove(session.getId());
                        session.close();
                    } catch (IOException e) {
                        LOG.error("Error closing session: " + session.getId());
                    }
                });

        sender.sendMessage(msg);
        roomService.remove(sender.getId());
        roomService.removeRoom(room.id);
        sender.close();
    }
}
