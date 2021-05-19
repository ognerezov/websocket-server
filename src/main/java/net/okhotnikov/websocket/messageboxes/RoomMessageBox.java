package net.okhotnikov.websocket.messageboxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.exceptions.ForbiddenException;
import net.okhotnikov.websocket.handler.MessageBox;
import net.okhotnikov.websocket.handler.SecurityFilter;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static net.okhotnikov.websocket.util.CommonMessages.*;

public abstract class RoomMessageBox implements MessageBox {

    protected RoomService roomService;
    protected SecurityFilter securityFilter;
    protected ObjectMapper mapper;


    public RoomMessageBox() {
    }

    public RoomMessageBox(RoomService roomService, SecurityFilter securityFilter, ObjectMapper mapper) {
        this.roomService = roomService;
        this.securityFilter = securityFilter;
        this.mapper = mapper;
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        if (securityFilter.filter(message,sender))
            return;

        sender.sendMessage(new TextMessage(forbidden(mapper)));
        throw new ForbiddenException();
    }

    public RoomMessageBox(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public SecurityFilter getSecurityFilter() {
        return securityFilter;
    }

    public void setSecurityFilter(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }
}
