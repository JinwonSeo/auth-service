package kr.sproutfx.platform.authservice.common.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

import kr.sproutfx.platform.authservice.common.security.service.UserService;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

@Component
public class JwtProvider {
    @Autowired
    UserService userService;
    
    @Value("${jwt.client-secret}")
    private String clientSecret;
    @Value("${jwt.base64-secret}")
    private String base64Secret;
    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValidSeconds;
    @Value("${jwt.refreash-token-validity-in-seconds}")
    private long refreashTokenValidSeconds;

    @PostConstruct
    protected void initialize() {
        base64Secret = Base64.getEncoder().encodeToString(base64Secret.getBytes());
    }

    public String createAccessToken(String subject){
        return String.format("Bearer %s", 
            Jwts.builder()
                .setClaims(Jwts.claims().setSubject(subject))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (accessTokenValidSeconds * 1000L)))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)), SignatureAlgorithm.HS512)
                .compact());
    }

    public String createRefreshToken() {
        return String.format("Bearer %s",
            Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (refreashTokenValidSeconds * 1000L)))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)), SignatureAlgorithm.HS512)
                .compact());
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(
            userService.loadUserByUsername(getSubjectByToken(token)), token, null);
    }

    public boolean validateAccessToken(String token) {
        if (validateToken(token) && (getSubjectByToken(token) == null)) return false;
        else return validateToken(token);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(removeBearerPrefix(token));
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)))
                .build()
                .parseClaimsJws(token);

            return true;
        } catch(SecurityException | MalformedJwtException e) {
            //logger.info("invalid jwt signature.");
        } catch(ExpiredJwtException e) {
            //logger.info("expired jwt token.");
        } catch(UnsupportedJwtException e) {
            //logger.info("unsupported jwt token.");
        } catch(IllegalArgumentException e) {
            //logger.info("invalid jwt token.");
        }
        return false;
    }

    private String getSubjectByToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean clientSecretMatches(String value) {
        if (this.clientSecret.equals(value)) return true;    
        else return false;
    }

    private String removeBearerPrefix(String tokenString) {
        if (StringUtils.hasText(tokenString) && tokenString.startsWith("Bearer ")) {
            return tokenString.substring(7);
        } else {
            return tokenString;
        }
    }
}