package net.okhotnikov.websocket.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.model.GenericMessage;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class CommonUtils {

    public static String getHeader(HttpHeaders headers, String id){
        List<String> values= headers.get(id);
        return values == null || values.isEmpty() ? null : values.get(0);
    }

    public static <T> String messageOrError(ObjectMapper mapper, T object){
        String msg ="error";
        try {
            msg = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
