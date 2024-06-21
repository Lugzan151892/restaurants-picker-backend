package lugzan.co.restaurant.backend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    static public String createJwtToken(Object data, String userName) {
        return Jwts.builder()
                .claim("data", data)
                .subject(userName)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .signWith(getSecretKey())
                .compact();
    }

    static private SecretKey getSecretKey() {
        String secretKey = "testJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKeytestJwtSecretKey";
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
}
