package com.wh.web.myweb.util;

import com.wh.web.myweb.constants.TokenConstant;
import com.wh.web.myweb.model.bo.UserBO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    private static final String CLAIM_USERNAME = "userName";
    private static final String CLAIM_CREATED_DATETIME = "createdDateTime";

    public String getUserNameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    public LocalDateTime getCreatedDateTimeFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) claims.get(CLAIM_CREATED_DATETIME)), ZoneId.systemDefault());
    }

    public LocalDateTime getExpirationDateTimeFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(TokenConstant.SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + TokenConstant.EXPIRATION_TIME);
    }

    private Boolean isTokenExpired(String token) {
        LocalDateTime expirationDateTime = getExpirationDateTimeFromToken(token);
        return expirationDateTime.isBefore(LocalDateTime.now());
    }

    private Boolean isCreatedBeforeLastPassWordReset(LocalDateTime createdDateTime, LocalDateTime lastPassWordResetDateTime) {
        return (lastPassWordResetDateTime != null && createdDateTime.isBefore(lastPassWordResetDateTime));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_CREATED_DATETIME, LocalDateTime.now());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, TokenConstant.SECRET_KEY).compact();
    }

    public Boolean canTokenBeRefresh(String token) {
        return !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_CREATED_DATETIME, new Date());
        return generateToken(claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        UserBO user = (UserBO) userDetails;
        String userName = getUserNameFromToken(token);
        return (userName.equals(user.getUsername()) && !isTokenExpired(token));
    }
}

