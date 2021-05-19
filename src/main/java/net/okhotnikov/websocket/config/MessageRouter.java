package net.okhotnikov.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.handler.MessageBox;
import net.okhotnikov.websocket.messageboxes.BroadcastBox;
import net.okhotnikov.websocket.messageboxes.RoomMessageBox;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.MessageType;
import net.okhotnikov.websocket.security.RoomRoleFilter;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessageRouter implements MessageBox {
    private final Map<MessageType, MessageBox> routes = new HashMap<>();

    public MessageRouter(RoomService roomService, ObjectMapper mapper) {
        routes.put(MessageType.Broadcast,new BroadcastBox(roomService, new RoomRoleFilter(roomService), mapper));
    }

    public MessageBox getRoute(MessageType type){
        return routes.get(type);
    }

//    public void roomConnect(RoomService roomService){
//        for(MessageBox messageBox: routes.values())
//            if(messageBox instanceof RoomMessageBox)
//                ((RoomMessageBox) messageBox).setRoomService(roomService);
//    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        getRoute(message.type)
                .receive(message, sender);
    }
}
