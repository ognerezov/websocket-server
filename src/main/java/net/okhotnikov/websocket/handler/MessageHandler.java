package net.okhotnikov.websocket.handler;

import net.okhotnikov.websocket.model.Participant;
import net.okhotnikov.websocket.service.RoomService;
import net.okhotnikov.websocket.service.TokenService;
import net.okhotnikov.websocket.util.Literals;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

import static net.okhotnikov.websocket.util.CommonUtils.getHeader;

@Service
public class MessageHandler extends AbstractWebSocketHandler {

    private final RoomService roomService;
    private final TokenService tokenService;

    public MessageHandler(RoomService roomService, TokenService tokenService) {
        this.roomService = roomService;
        this.tokenService = tokenService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        String token = getHeader(session.getHandshakeHeaders(), Literals.Authorization);
        Participant  participant = tokenService.decode(token);
        participant.session = session;
        roomService.enter(participant);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message);
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        System.out.println(message);
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
