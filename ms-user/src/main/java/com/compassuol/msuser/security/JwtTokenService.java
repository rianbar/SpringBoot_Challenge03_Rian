package com.compassuol.msuser.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.compassuol.msuser.exception.type.BadGatewayException;
import com.compassuol.msuser.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtTokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String createToken(UserModel user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-application")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new BadGatewayException("error when create jwt token");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-application")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            throw new BadGatewayException("error when validate jwt token");
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
