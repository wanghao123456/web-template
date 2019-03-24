package com.wh.web.myweb.util;

import com.wh.web.myweb.constants.TokenConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {

    private String generateToken(Claims claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, TokenConstant.SECRET_KEY).compact();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(TokenConstant.SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + TokenConstant.EXPIRATION_TIME);
    }

    public String getUserNameFromToken(String token) {
        return getClaimsFromToken(token).getAudience();
    }

    public String generateToken(UserDetails userDetails) {
        DefaultClaims claims = new DefaultClaims();
        claims.setIssuer(TokenConstant.CLAIMS_ISSUER).setSubject(TokenConstant.CLAIMS_SUBJECT)
                .setAudience(userDetails.getUsername()).setExpiration(getExpirationDate())
                .setNotBefore(new Date()).setIssuedAt(new Date());
        return generateToken(claims);
    }

    public String refreshToken(String token) {
        return generateToken(getClaimsFromToken(token).setExpiration(getExpirationDate()));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = getClaimsFromToken(token);
        Date now = new Date();
        return (claims.getAudience().equals(userDetails.getUsername()) && now.after(claims.getNotBefore()) && now.before(claims.getExpiration()));
    }
}