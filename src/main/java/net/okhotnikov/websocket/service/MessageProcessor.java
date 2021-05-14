package net.okhotnikov.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.out.BasicResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class MessageProcessor {
    private final ObjectMapper mapper;

    public MessageProcessor(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> GenericMessage<T> read(String text, WebSocketSession session) throws IOException {
        try{
            return mapper.readValue(text, new TypeReference<GenericMessage<T>>() {
            });
        }catch (Exception e){
            String message = "{\"value\":\"fail\"}";
            try {
                 message = mapper.writeValueAsString(BasicResponse.getFail(e.getClass().getName()));
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
            session.sendMessage(new TextMessage(message));
            return null;
        }
    }
}
