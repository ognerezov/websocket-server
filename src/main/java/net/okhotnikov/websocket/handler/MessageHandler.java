package net.okhotnikov.websocket.handler;

import net.okhotnikov.websocket.config.MessageRouter;
import net.okhotnikov.websocket.messageboxes.RoomMessageBox;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.Participant;
import net.okhotnikov.websocket.service.MessageProcessor;
import net.okhotnikov.websocket.service.RoomService;
import net.okhotnikov.websocket.service.TokenService;
import net.okhotnikov.websocket.util.Literals;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Map;

import static net.okhotnikov.websocket.util.CommonUtils.getHeader;

@Service
public class MessageHandler extends AbstractWebSocketHandler {
    protected static final Logger LOG = LogManager.getLogger(MessageHandler.class);
    private final RoomService roomService;
    private final TokenService tokenService;
    private final MessageProcessor processor;
    private final MessageRouter router;

    public MessageHandler(RoomService roomService, TokenService tokenService, MessageProcessor processor, MessageRouter router) {
        this.roomService = roomService;
        this.tokenService = tokenService;
        this.processor = processor;
        this.router = router;
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
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        roomService.exit(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message);
        try {
            GenericMessage<Map<String,Object>> in = processor.read(message.getPayload(), session);
            if (in == null){
                LOG.error("Error parsing message: " + message.getPayload());
                return;
            }
            System.out.println(in);
            router.receive(in,session);
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
