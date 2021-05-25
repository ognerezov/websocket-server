package net.okhotnikov.websocket.messageboxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.security.PermitAllFilter;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class DisconnectMessageBox extends RoomMessageBox{

    public DisconnectMessageBox(RoomService roomService, ObjectMapper mapper) {
        super(roomService, new PermitAllFilter(), mapper);
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        sender.close();
    }
}
