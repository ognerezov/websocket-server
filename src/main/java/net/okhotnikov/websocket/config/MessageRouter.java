package net.okhotnikov.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.handler.MessageBox;
import net.okhotnikov.websocket.messageboxes.BroadcastBox;
import net.okhotnikov.websocket.messageboxes.ControlMessageBox;
import net.okhotnikov.websocket.messageboxes.RoomMessageBox;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.MessageType;
import net.okhotnikov.websocket.security.RoomRoleFilter;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static net.okhotnikov.websocket.util.CommonMessages.*;

@Configuration
public class MessageRouter implements MessageBox {
    private final Map<MessageType, MessageBox> routes = new HashMap<>();
    private final ObjectMapper mapper;

    public MessageRouter(RoomService roomService, ObjectMapper mapper) {
        this.mapper = mapper;

        RoomRoleFilter roomRoleFilter = new RoomRoleFilter(roomService);

        routes.put(MessageType.Broadcast,new BroadcastBox(roomService, roomRoleFilter, mapper));
        routes.put(MessageType.Control, new ControlMessageBox(roomService,roomRoleFilter,mapper));
    }

    public MessageBox getRoute(MessageType type){
        return routes.get(type);
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        MessageBox rout = getRoute(message.type);
        if(rout == null){
            sender.sendMessage(new TextMessage(notSupportedType(mapper)));
            return;
        }

        rout.receive(message, sender);
    }
}
