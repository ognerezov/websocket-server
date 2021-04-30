package net.okhotnikov.websocket.util;

import org.springframework.http.HttpHeaders;

import java.util.List;

public class CommonUtils {

    public static String getHeader(HttpHeaders headers, String id){
        List<String> values= headers.get(id);
        return values == null || values.isEmpty() ? null : values.get(0);
    }
}
