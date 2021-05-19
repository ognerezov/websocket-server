package net.okhotnikov.websocket.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.MessageType;

import static net.okhotnikov.websocket.util.Literals.*;
import static net.okhotnikov.websocket.util.CommonUtils.*;

public class CommonMessages {
    public static String sent(ObjectMapper mapper){
        return messageOrError(mapper,new GenericMessage<>(MessageType.ServerResponse,SENT));
    }

    public static String forbidden(ObjectMapper mapper){
        return messageOrError(mapper,new GenericMessage<>(MessageType.ServerResponse,FORBIDDEN));
    }
}
