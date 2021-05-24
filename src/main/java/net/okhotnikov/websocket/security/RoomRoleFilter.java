package net.okhotnikov.websocket.security;

import net.okhotnikov.websocket.handler.SecurityFilter;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.Participant;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.web.socket.WebSocketSession;

public class RoomRoleFilter implements SecurityFilter {

    protected final RoomService roomService;

    public RoomRoleFilter(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public <T> boolean filter(GenericMessage<T> message, WebSocketSession sender) {
        Participant participant = roomService.getParticipant(sender.getId());

        if (participant == null)
            return false;

        return participant.hasPermissionFor(message.type);
    }
}
