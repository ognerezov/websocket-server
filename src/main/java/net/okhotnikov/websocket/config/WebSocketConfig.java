package net.okhotnikov.websocket.config;

import net.okhotnikov.websocket.handler.MessageHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageHandler messageHandler;
    private final HandShaker handShaker;

    public WebSocketConfig(MessageHandler messageHandler, HandShaker handShaker) {
        this.messageHandler = messageHandler;
        this.handShaker = handShaker;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler,"/ws")
                .setAllowedOrigins("*")
                .addInterceptors(handShaker);
    }
}
