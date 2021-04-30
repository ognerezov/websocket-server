package net.okhotnikov.websocket.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void testEncode(){
        System.out.println(
                tokenService.encode("Teacher",1,true)
        );

        System.out.println(
                tokenService.encode("Student",1,false)
        );

        System.out.println(
                tokenService.encode("Stranger",3,true)
        );

    }

    @Test
    public void testToken(){
        System.out.println(tokenService.decode("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiItYXV0aC0iLCJwcm9kdWN0Ijoic2Nob29sIiwiY2xpZW50VHlwZSI6InVzZXIiLCJoYXJkd2FyZUlkIjoiMjlmOWI2NTZjMGEzZTA2YTMzY2I2ZDA5ZmZhZWNmYjEzYjIzNyIsInRva2VuX2V4cGlyYXRpb25fZGF0ZSI6MTYyMjM4ODE3NTQyNCwiZXhwIjoxNjIyMzg4MTc1LCJ1c2VybmFtZSI6IlJ1c3NpYSIsInRva2VuX2NyZWF0ZV9kYXRlIjoxNjE5NzA5Nzc1NDI0fQ.NCM6ATOcrm5Pqgbt_aufAxt-VggH0V6D50Ay-ARarurQgtJGt6ck2jLDL33VBQQeDRQ20Rkk8JUrZqZtqVHu4g"
        ));
    }
}
