package net.okhotnikov.websocket.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.okhotnikov.websocket.model.Participant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

import static net.okhotnikov.websocket.util.Literals.*;

@Service
public class TokenService {

    private final String tokenSecret;
    private final String subject;
    private final JwtParser parser;

    public TokenService(
            @Value("${token.secret}") String tokenSecret,
            @Value("${token.subject}")  String subject) {
        this.tokenSecret = tokenSecret;
        this.parser = Jwts.parser()
                .setSigningKey(tokenSecret)
                .requireSubject(subject);

        this.subject = subject;
    }

    public Participant decode(String token){
        Claims claims = parser
                .parseClaimsJws(token)
                .getBody();

        String name =  (String)  claims
                        .get(NAME);
        String room = (String) claims.get(ROOM);
        Object obj = claims.get(ADMIN);

        boolean admin = obj != null && (boolean) obj;

        return new Participant(name,room,admin);
    }


    public String encode(String name, long room, boolean isAdmin){
        Map<String, Object> data = new HashMap<>();

        data.put(NAME, name);
        data.put(ROOM,String.valueOf(room));
        data.put(ADMIN,isAdmin);

        return Jwts.builder()
                .setClaims(data)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }
}
