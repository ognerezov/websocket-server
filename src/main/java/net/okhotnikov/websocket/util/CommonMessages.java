package net.okhotnikov.websocket.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.MessageType;
import net.okhotnikov.websocket.model.out.BasicResponse;

import static net.okhotnikov.websocket.util.Literals.*;
import static net.okhotnikov.websocket.util.CommonUtils.*;

public class CommonMessages {

    public static String text(ObjectMapper mapper, String text, boolean status){
        return messageOrError(mapper,new GenericMessage<>(MessageType.ServerResponse,
                new BasicResponse(status ? BasicResponse.OK : BasicResponse.FAIL, text)));
    }

    public static String sent(ObjectMapper mapper){
        return messageOrError(mapper,new GenericMessage<>(MessageType.ServerResponse,BasicResponse.getOk(SENT)));
    }

    public static String forbidden(ObjectMapper mapper){
        return messageOrError(mapper,new GenericMessage<>(MessageType.ServerResponse,BasicResponse.getFail(FORBIDDEN)));
    }

    public static String badTarget(ObjectMapper mapper){
        return messageOrError(mapper,new GenericMessage<>(MessageType.ServerResponse,BasicResponse.getFail(PARTICIPANTS_ARE_MISSING)));
    }

    public static String parsingError(ObjectMapper mapper){
        return messageOrError(mapper,new GenericMessage<>(MessageType.ServerResponse,BasicResponse.getFail(PARSING_ERROR)));
    }

    public static String invalidControlMessage(ObjectMapper mapper){
        return messageOrError(mapper,new GenericMessage<>(MessageType.ServerResponse,BasicResponse.getFail(INVALID_CONTROL_MESSAGE)));
    }

    public static String notSupportedType(ObjectMapper mapper){
        return messageOrError(mapper,new GenericMessage<>(MessageType.ServerResponse,BasicResponse.getFail(NOT_SUPPORTED_MESSAGE_TYPE)));
    }
}
