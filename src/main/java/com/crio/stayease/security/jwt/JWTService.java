package com.crio.stayease.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    String alogSecret = "1b31239gebd982123???{}(@^$#)";

    private final Algorithm algorithm = Algorithm.HMAC256(alogSecret);

    public String generateToken(Long id) {
        return JWT.create()
                .withSubject(id.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .sign(algorithm);
    }

    public Long getUserIDFromJWT(String token){
        String id = JWT.require(algorithm).build().verify(token).getSubject();
        return Long.parseLong(id);
    }

    public String getTokenFromHeader(String authHeader){
        return authHeader.split(" ")[1];
    }
}
