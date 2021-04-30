package net.okhotnikov.websocket.config;

import net.okhotnikov.websocket.service.TokenService;
import net.okhotnikov.websocket.util.Literals;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

import static net.okhotnikov.websocket.util.CommonUtils.getHeader;


@Configuration
public class HandShaker extends HttpSessionHandshakeInterceptor {

    private final TokenService tokenService;
    private static final Logger LOG = LogManager.getLogger(HandShaker.class);

    public HandShaker(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try{
            System.out.println(request.getHeaders());
            String token = getHeader(request.getHeaders(), Literals.Authorization);
            LOG.info(tokenService.decode(token).enterMessage());
            return super.beforeHandshake(request, response, wsHandler, attributes);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
