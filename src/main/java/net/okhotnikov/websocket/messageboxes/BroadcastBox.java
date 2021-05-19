package net.okhotnikov.websocket.messageboxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.exceptions.ForbiddenException;
import net.okhotnikov.websocket.handler.SecurityFilter;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.Participant;
import net.okhotnikov.websocket.model.Room;
import net.okhotnikov.websocket.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static net.okhotnikov.websocket.util.CommonUtils.*;
import static net.okhotnikov.websocket.util.CommonMessages.*;

public class BroadcastBox extends RoomMessageBox {


    private static final Logger LOG = LogManager.getLogger(BroadcastBox.class);

    public BroadcastBox(RoomService roomService, SecurityFilter securityFilter, ObjectMapper mapper) {
        super(roomService, securityFilter, mapper);
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        try {
            super.receive(message, sender);
        }catch (ForbiddenException e){
            LOG.error("User broadcast forbidden: " + sender.getId());
            return;
        }
        Participant participant = roomService.getParticipant(sender.getId());
        Room room = roomService.getRoom(participant);

        String msg = messageOrError(mapper,message);

        for(WebSocketSession session: room.sessions.values()){
            if(session.getId().equals(sender.getId()))
                continue;
            session.sendMessage(new TextMessage(msg));
        }

        sender.sendMessage(new TextMessage(sent(mapper)));
    }
}
