package org.java.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Component
public class JwtUtil {

    private static String BACKEND_SECRET_KEY;

    private static Integer BACKEND_TIME_OUT_DAYS;

    @Autowired
    public JwtUtil(@Value("${ai.jwt.secretkey}") String backendSecretKey,
                   @Value("${ai.jwt.timeoutDays}") Integer backendTimeOutDays) {
        BACKEND_SECRET_KEY = backendSecretKey;
        BACKEND_TIME_OUT_DAYS = backendTimeOutDays;
    }


    public static String crateToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, BACKEND_TIME_OUT_DAYS * 24);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(BACKEND_SECRET_KEY));
    }

    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(BACKEND_SECRET_KEY)).build().verify(token);
    }

    private static DecodedJWT getToken(String token) {
        return JWT.require(Algorithm.HMAC256(BACKEND_SECRET_KEY)).build().verify(token);
    }

    public static String decode(String token, String key) {
        return getToken(token).getClaims().get(key).asString();
    }
}
