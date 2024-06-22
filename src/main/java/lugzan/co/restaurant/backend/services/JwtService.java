package lugzan.co.restaurant.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final int millisecondsInHour = 60000 * 60;

    static public String createJwtToken(Object data, String userName) {
        return Jwts.builder()
                .claim("data", data)
                .subject(userName)
                .expiration(new Date(System.currentTimeMillis() + 10))
                .signWith(getSecretKey())
                .compact();
    }

    static public String createJwtRefreshToken(String email, String userName) {
        return Jwts.builder()
                .claim("data", email)
                .subject(userName)
                .expiration(new Date(System.currentTimeMillis() + millisecondsInHour * 24 * 10))
                .signWith(getRefreshSecretKey())
                .compact();
    }

    static private SecretKey getSecretKey() {
        String secretKey = "testJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKey";
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    static private SecretKey getRefreshSecretKey() {
        String secretKey = "testRefreshJwtSecretKeytestRefreshJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtRefreshSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKey";
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    static public Claims getTokenData (String token) {
        return Jwts.parser()
                .verifyWith(JwtService.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    static public Claims getRefreshTokenData (String token) {
        return Jwts.parser()
                .verifyWith(JwtService.getRefreshSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
